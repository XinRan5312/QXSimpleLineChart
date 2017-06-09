package com.ran.qxlinechart.car.utils;

/**
 * Created by houqixin on 2017/6/1.
 */
public class PositionEntity {

    public double longitude;

    public double latitue;

    public String address;

    public String city;
    public String addressCode;

    public String cityCode;

    public PositionEntity() {
    }

    public PositionEntity(double latitue,double longitude, String address, String city, String addressCode, String cityCode) {
        this.longitude = longitude;
        this.latitue = latitue;
        this.address = address;
        this.city = city;
        this.addressCode = addressCode;
        this.cityCode = cityCode;
    }
}
