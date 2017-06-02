package com.ran.qxlinechart.car.utils;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationListener;

/**
 * Created by houqixin on 2017/6/1.
 * 简单的封装的定位请求可以单次或者多次请求,但是注意在app结束或者定位结束的时候销毁定位
 */
public class QXLocationTask implements AMapLocationListener,OnLocationGetListener {

    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {

    }

    @Override
    public void onLocationGet(PositionEntity positionEntity) {

    }

    @Override
    public void onRegecodeGet(PositionEntity positionEntity) {

    }
}
