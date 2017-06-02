package com.ran.qxlinechart.car.utils;

/**
 * Created by houqixin on 2017/6/1.
 */
public class PositionEntity {

    public double longitude;

    public double latitue;

    public String address;

    public String city;

    public PositionEntity() {
    }

    public PositionEntity(double longitude, double latitue, String address, String city) {
        this.longitude = longitude;
        this.latitue = latitue;
        this.address = address;
        this.city = city;
    }
}
