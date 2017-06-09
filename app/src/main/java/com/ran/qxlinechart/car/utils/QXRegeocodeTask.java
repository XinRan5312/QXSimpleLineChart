package com.ran.qxlinechart.car.utils;

import android.content.Context;

import com.amap.api.services.core.AMapException;
import com.amap.api.services.core.LatLonPoint;
import com.amap.api.services.geocoder.GeocodeResult;
import com.amap.api.services.geocoder.GeocodeSearch;
import com.amap.api.services.geocoder.RegeocodeQuery;
import com.amap.api.services.geocoder.RegeocodeResult;

/**
 * Created by houqixin on 2017/6/5.
 * 逆地理编码
 */
public class QXRegeocodeTask implements GeocodeSearch.OnGeocodeSearchListener {
    private GeocodeSearch mGeocodeSearch;
    private static final float SEARCH_RADIUS = 50;
    private OnLocationGetListener mLocationGetListener;

    public QXRegeocodeTask(Context context) {
        mGeocodeSearch = new GeocodeSearch(context);
        mGeocodeSearch.setOnGeocodeSearchListener(this);
    }

    public void setmLocationGetListener(OnLocationGetListener mLocationGetListener) {
        this.mLocationGetListener = mLocationGetListener;
    }

    public void StartSearch(double longitude, double latitue) {
        RegeocodeQuery regeocodeQuery = new RegeocodeQuery(new LatLonPoint(latitue, longitude), SEARCH_RADIUS, GeocodeSearch.AMAP);

        mGeocodeSearch.getFromLocationAsyn(regeocodeQuery);
    }

    @Override
    public void onRegeocodeSearched(RegeocodeResult regeocodeResult, int i) {

        if(i== AMapException.CODE_AMAP_SUCCESS){

            if(regeocodeResult!=null&&regeocodeResult.getRegeocodeAddress()!=null&&mLocationGetListener!=null){

                String address=regeocodeResult.getRegeocodeAddress().getFormatAddress();
                String addressCode=regeocodeResult.getRegeocodeAddress().getAdCode();
                String city=regeocodeResult.getRegeocodeAddress().getCity();
                String cityCode=regeocodeResult.getRegeocodeAddress().getCityCode();

                PositionEntity positionEntity=new PositionEntity();
                positionEntity.address=address;
                positionEntity.addressCode=addressCode;
                positionEntity.city=city;
                positionEntity.cityCode=cityCode;

                mLocationGetListener.onRegecodeGet(positionEntity);
            }
        }
    }

    @Override
    public void onGeocodeSearched(GeocodeResult geocodeResult, int i) {

    }
}
