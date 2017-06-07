package com.example.menoliwm.mymaplist;

import java.io.Serializable;

/**
 * Created by Menoliwm on 2017-05-29.
 */

class MapInfo implements Serializable {

    private int id;
    private String userid;
    private String title;
    private String address;
    private double latitude;
    private double longitude;

    boolean isMapView = false;

    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id = id;
    }

    public String getUserid(){
        return userid;
    }
    public void setUserid(String userid){
        this.userid = userid;
    }

    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title = title;
    }

    public String getAddress(){
        return address;
    }
    public void setAddress(String address){
        this.address = address;
    }

    public double getLatitude(){
        return latitude;
    }
    public void setLatitude(double latitude){
        this.latitude = latitude;
    }

    public double getLongitude(){
        return longitude;
    }
    public void setLongitude(double longitude){
        this.longitude = longitude;
    }

    public boolean getIsMapView(){
        return isMapView;
    }
    public void setIsMapView(boolean isMapView){
        this.isMapView = isMapView;
    }
}
