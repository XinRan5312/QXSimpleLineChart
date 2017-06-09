package com.ran.qxlinechart.car.utils;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.route.BusPath;
import com.amap.api.services.route.BusRouteResult;
import com.amap.api.services.route.DrivePath;
import com.amap.api.services.route.DriveRouteResult;

import com.amap.api.services.route.RidePath;
import com.amap.api.services.route.RideRouteResult;
import com.amap.api.services.route.RouteSearch;
import com.amap.api.services.route.WalkPath;
import com.amap.api.services.route.WalkRouteResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by houqixin on 2017/6/6.
 * 简单封装驾驶 公交和步行以及骑行路线规划
 */
public class QXRoutTask implements RouteSearch.OnRouteSearchListener {

    private RouteType mRouteType;
    private static QXRoutTask mRouteTask;

    private RouteSearch mRoutSearch;

    private List<OnDriveRouteCalculateListener> mDriveListeners = new ArrayList<OnDriveRouteCalculateListener>();

    private List<OnBusRouteCalculateListener> mBusListeners = new ArrayList<OnBusRouteCalculateListener>();

    private List<OnWalkRouteCalculateListener> mWalkListeners = new ArrayList<OnWalkRouteCalculateListener>();

    private List<OnRideRouteCalculateListener> mRideListeners = new ArrayList<OnRideRouteCalculateListener>();

    private int serchTypeNum = 0;


    public static QXRoutTask newInstance(Context context) {

        if (mRouteTask == null) mRouteTask = new QXRoutTask(context);
        return mRouteTask;
    }

    private QXRoutTask(Context context) {

        mRoutSearch = new RouteSearch(context);

        mRoutSearch.setRouteSearchListener(this);

    }

    public void startRouteSearch(RouteType type, LatLonPoint from, LatLonPoint to) {
        startRouteSearch(type, true, from, to);
    }

    public void startRouteSearch(RouteType type, boolean isAsyn, LatLonPoint from, LatLonPoint to) {

        RouteSearch.FromAndTo fromAndTo = new RouteSearch.FromAndTo(from, to);

        serchTypeNum = type.getValue();

        switch (type) {

            case WALK_ROUTE:

                RouteSearch.WalkRouteQuery walkRouteQuery = new RouteSearch.WalkRouteQuery(fromAndTo, type.getValue());
                if (isAsyn) {
                    mRoutSearch.calculateWalkRouteAsyn(walkRouteQuery);
                } else {

                    try {
                        mRoutSearch.calculateWalkRoute(walkRouteQuery);
                    } catch (AMapException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case RIDE_ROUTE:

                RouteSearch.RideRouteQuery rideRouteQuery = new RouteSearch.RideRouteQuery(fromAndTo, type.getValue());

                if (isAsyn) {
                    mRoutSearch.calculateRideRouteAsyn(rideRouteQuery);
                } else {
                    try {
                        mRoutSearch.calculateRideRoute(rideRouteQuery);
                    } catch (AMapException e) {
                        e.printStackTrace();
                    }
                }
                break;
            case BUS_ROUTE:

                RouteSearch.BusRouteQuery busRouteQuery = new RouteSearch.BusRouteQuery(fromAndTo, type.getValue(), "北京", 0);

                if (isAsyn) {

                    mRoutSearch.calculateBusRouteAsyn(busRouteQuery);
                } else {

                    try {
                        mRoutSearch.calculateBusRoute(busRouteQuery);
                    } catch (AMapException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case DRIVE_ROUTE:

                RouteSearch.DriveRouteQuery driveRouteQuery = new RouteSearch.DriveRouteQuery(fromAndTo, type.getValue(), null, null, "");
                if (isAsyn) {

                    mRoutSearch.calculateDriveRouteAsyn(driveRouteQuery);
                } else {
                    try {
                        mRoutSearch.calculateDriveRoute(driveRouteQuery);
                    } catch (AMapException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

    }

    public void setRouteType(RouteType mRouteType) {
        this.mRouteType = mRouteType;
    }

    public RouteType getRouteType() {
        return mRouteType;
    }

    @Override
    public void onBusRouteSearched(BusRouteResult busRouteResult, int i) {

        if (i == AMapException.CODE_AMAP_SUCCESS && busRouteResult != null) {

            synchronized (this) {

                List<BusPath> paths = busRouteResult.getPaths();

                BusRouteResponse response = null;
                if (paths != null && !paths.isEmpty()) {
                    response = new BusRouteResponse();
                    BusPath path = paths.get(0);//第一个路线就是你搜索的时候选择的路线  比如最短距离等

                    response.totalDistance = path.getDistance() / 1000;//变成公里

                    response.busDistance = path.getBusDistance() / 1000;//变成公里

                    response.walkDistance = path.getWalkDistance();//就用米
                    response.cost = path.getCost();
                    response.time = (int) (path.getDuration() / 60);//变成分钟

                }
                if (response != null) {
                    for (OnBusRouteCalculateListener listner : mBusListeners) {

                        listner.onRouteCalculate(response);

                    }
                }
            }
        }

    }

    @Override
    public void onDriveRouteSearched(DriveRouteResult driveRouteResult, int i) {

        if (i == AMapException.CODE_AMAP_SUCCESS && driveRouteResult != null) {

            synchronized (this) {

                List<DrivePath> paths = driveRouteResult.getPaths();
                float cost = 0;
                float distance = 0;

                int time = 0;
                if(isNotNullAndEmpty(paths)) {
                    DrivePath path = paths.get(0);//第一个路线就是你搜索的时候选择的路线  比如最短距离等

                    distance = path.getDistance() / 1000;//变成公里

                    time = (int) (path.getDuration() / 60);//变成分钟
                    path.getTotalTrafficlights();
                }
                cost = driveRouteResult.getTaxiCost();//公交费用

                for (OnDriveRouteCalculateListener listner : mDriveListeners) {

                    listner.onRouteCalculate(cost, distance, time);
                }
            }

        }
    }

    @Override
    public void onWalkRouteSearched(WalkRouteResult walkRouteResult, int i) {

        if (i == AMapException.CODE_AMAP_SUCCESS && walkRouteResult != null) {

            synchronized (this) {

                List<WalkPath> paths = walkRouteResult.getPaths();

                if(isNotNullAndEmpty(paths)) {

                    WalkPath path = paths.get(0);

                    for (OnWalkRouteCalculateListener listener : mWalkListeners) {

                        listener.onRouteCalculate(path.getDistance() / 1000, (int) (path.getDuration() / 60));
                    }
                }
            }
        }

    }

    @Override
    public void onRideRouteSearched(RideRouteResult rideRouteResult, int i) {
        if (i == AMapException.CODE_AMAP_SUCCESS && rideRouteResult != null) {

            synchronized (this) {

                List<RidePath> paths = rideRouteResult.getPaths();

                if(isNotNullAndEmpty(paths)){

                    RidePath path=paths.get(0);

                    for(OnRideRouteCalculateListener listener:mRideListeners){

                        listener.onRouteCalculate(path.getDistance()/1000, (int) (path.getDuration()/60));
                    }

                }

            }
        }
    }
  private boolean isNotNullAndEmpty(List list){

      if(list!=null&&!list.isEmpty())return true;

      return false;
  }
    public enum RouteType {

        WALK_ROUTE(0), RIDE_ROUTE(0), BUS_ROUTE(0), DRIVE_ROUTE(0);

        private int value;

        RouteType(int value) {
            this.value = value;
        }

        public int getValue() {

            return value;
        }

        public void setValue(int value) {
            this.value = value;
        }
    }

    public interface OnDriveRouteCalculateListener {
        public void onRouteCalculate(float cost, float totolDistance, int duration);

    }

    public interface OnBusRouteCalculateListener {
        public void onRouteCalculate(BusRouteResponse response);

    }

    public interface OnWalkRouteCalculateListener {
        public void onRouteCalculate(float totolDistance, int duration);

    }

    public interface OnRideRouteCalculateListener {
        public void onRouteCalculate(float totolDistance, int duration);

    }

    public void addBusRouteCalculateListener(OnBusRouteCalculateListener listener) {
        synchronized (this) {
            if (mBusListeners.contains(listener))
                return;
            mBusListeners.add(listener);
        }
    }

    public void removeBusRouteCalculateListener(OnBusRouteCalculateListener listener) {
        synchronized (this) {
            mBusListeners.remove(listener);
        }
    }

    public void addDriveRouteCalculateListener(OnDriveRouteCalculateListener listener) {
        synchronized (this) {
            if (mDriveListeners.contains(listener))
                return;
            mDriveListeners.add(listener);
        }
    }

    public void removeDirveRouteCalculateListener(OnDriveRouteCalculateListener listener) {
        synchronized (this) {
            mDriveListeners.remove(listener);
        }
    }

    public void addWalkRouteCalculateListener(OnWalkRouteCalculateListener listener) {
        synchronized (this) {
            if (mWalkListeners.contains(listener))
                return;
            mWalkListeners.add(listener);
        }
    }

    public void removeWalkRouteCalculateListener(OnWalkRouteCalculateListener listener) {
        synchronized (this) {
            mWalkListeners.remove(listener);
        }
    }

    public void addRideRouteCalculateListener(OnRideRouteCalculateListener listener) {
        synchronized (this) {
            if (mRideListeners.contains(listener))
                return;
            mRideListeners.add(listener);
        }
    }

    public void removeRideRouteCalculateListener(OnRideRouteCalculateListener listener) {
        synchronized (this) {
            mRideListeners.remove(listener);
        }
    }
}
