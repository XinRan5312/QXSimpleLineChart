package com.ran.qxlinechart.car.utils;

/**
 * Created by houqixin on 2017/6/1.
 *
 * 逆地理编码或者定位回调接口
 */
public interface OnLocationGetListener {

    void onLocationGet(PositionEntity positionEntity);

    void onRegecodeGet(PositionEntity positionEntity);

}
