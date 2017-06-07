package com.example.menoliwm.mymaplist;


/**
 * Created by Menoliwm on 2017-05-23.
 */

public class MapInfoManager {


    public MapInfo getMapInfoList(double HighLatitude, double LowLatitude, double RightLongitude, double LeftLongitude){

        MapInfo mapInfo = new MapInfo();

        double listlatitude = 35.679365;
        double listlongitude = 139.394207;

        if(listlatitude <= HighLatitude && listlatitude >= LowLatitude) {
            if (listlongitude <= RightLongitude && listlongitude >= LeftLongitude) {
                mapInfo.setLatitude(listlatitude);
                mapInfo.setLongitude(listlongitude);
            }
        }
        return mapInfo;
    }
}