package com.ran.qxlinechart.car.utils;

import android.content.Context;
import android.graphics.Region;
import android.text.TextUtils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by houqixin on 2017/6/1.
 * 简单的封装的定位请求可以单次或者多次请求,但是注意在app结束或者定位结束的时候销毁定位
 */
public class QXLocationTask implements AMapLocationListener, OnLocationGetListener {
    private AMapLocationClient mLocationClient;

    private static QXLocationTask mLocationTask;

    private static final long CONNECTION_TIME_OUT = 5 * 1000;

    private Context mContext;

    private OnLocationGetListener mLocationGetListener;

    private QXRegeocodeTask mRegecodeTask;

    private QXLocationTask(Context context) {
        mLocationClient = new AMapLocationClient(context);
        mLocationClient.setLocationListener(this);
        mRegecodeTask = new QXRegeocodeTask(context);
        mRegecodeTask.setmLocationGetListener(this);
        mContext = context;
    }

    public QXLocationTask getInstance(Context context) {

        if (mLocationTask == null) {

            mLocationTask = new QXLocationTask(context);
        }

        return mLocationTask;
    }

    /**
     * 单次定位
     */
    public void startSingleLocate() {

        AMapLocationClientOption option = new AMapLocationClientOption();

        option.setHttpTimeOut(CONNECTION_TIME_OUT);

        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        option.setOnceLocation(true);

        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();
    }

    /**
     * 多次定位
     */
    public void startOnceMoreLocate() {

        AMapLocationClientOption option = new AMapLocationClientOption();

        option.setHttpTimeOut(CONNECTION_TIME_OUT);

        option.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);

        option.setInterval(CONNECTION_TIME_OUT + 1000 * 3);

        option.setOnceLocation(false);

        mLocationClient.setLocationOption(option);
        mLocationClient.startLocation();


    }

    public void setmOnLocationGetlisGetListener(OnLocationGetListener locationGetListener) {
        this.mLocationGetListener = locationGetListener;
    }

    public void stopLocation() {

        if (mLocationClient.isStarted()) {
            mLocationClient.stopLocation();
        }
    }

    public void stopAndClearMemmory() {

        stopLocation();
        mLocationClient.onDestroy();
        mLocationTask = null;
    }

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null && aMapLocation.getErrorCode() == 0&&mLocationGetListener!=null) {

            double longitude = aMapLocation.getLongitude();

            double latitue = aMapLocation.getLatitude();

            String city = isNotNullStr(aMapLocation.getCity());

            String cityCode = isNotNullStr(aMapLocation.getCityCode());

            String address = isNotNullStr(aMapLocation.getAddress());

            String addressCode=isNotNullStr(aMapLocation.getAdCode());

            PositionEntity point=new PositionEntity();

            point.longitude=longitude;

            point.latitue=latitue;

            point.city=city;

            point.cityCode=cityCode;

            point.address=address;

            point.addressCode=addressCode;

            mLocationGetListener.onLocationGet(point);
        }
    }

    private String isNotNullStr(String str) {
        return TextUtils.isEmpty(str) ? null : str;
    }

    @Override
    public void onLocationGet(PositionEntity positionEntity) {

    }

    @Override
    public void onRegecodeGet(PositionEntity positionEntity) {

    }
}
