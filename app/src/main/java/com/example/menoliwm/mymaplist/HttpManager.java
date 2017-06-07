package com.example.menoliwm.mymaplist;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by Menoliwm on 2017-05-31.
 */

public class HttpManager {

    String str;

    public void getMyMapInfoList() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    //URL url = new URL("http://169.254.92.16:8080/MapProject/List?phone=android");
                    URL url = new URL("http://192.168.11.13:8080/MapProject/List?phone=android");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    str = InputStreamToString(con.getInputStream());
                    Log.d("HTTP", str);
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void addAndroidList(final MapInfo mapInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {

                try {
                    StringBuffer sbParams = new StringBuffer();

                    sbParams.append("id").append("=").append(mapInfo.getId());
                    sbParams.append("&");
                    sbParams.append("userid").append("=").append(mapInfo.getUserid());
                    sbParams.append("&");
                    sbParams.append("title").append("=").append(mapInfo.getTitle());
                    sbParams.append("&");
                    sbParams.append("address").append("=").append(mapInfo.getAddress());
                    sbParams.append("&");
                    sbParams.append("latitude").append("=").append(mapInfo.getLatitude());
                    sbParams.append("&");
                    sbParams.append("longitude").append("=").append(mapInfo.getLongitude());

                    //URL url = new URL("http://169.254.92.16:8080/MapProject/AndAdd");
                    URL url = new URL("http://192.168.11.13:8080/MapProject/AndAdd");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept-Charset", "UTF-8");
                    con.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

                    String strParams = sbParams.toString();
                    OutputStream os = con.getOutputStream();
                    os.write(strParams.getBytes("UTF-8"));
                    os.flush();
                    os.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                    String line;
                    String page = "";

                    while ((line = reader.readLine()) != null) {
                        page += line;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    public void editAndroidList(final MapInfo mapInfo) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuffer sbParams = new StringBuffer();

                    sbParams.append("id").append("=").append(mapInfo.getId());
                    sbParams.append("&");
                    sbParams.append("userid").append("=").append(mapInfo.getUserid());
                    sbParams.append("&");
                    sbParams.append("title").append("=").append(mapInfo.getTitle());
                    sbParams.append("&");
                    sbParams.append("address").append("=").append(mapInfo.getAddress());
                    sbParams.append("&");
                    sbParams.append("latitude").append("=").append(mapInfo.getLatitude());
                    sbParams.append("&");
                    sbParams.append("longitude").append("=").append(mapInfo.getLongitude());
                    sbParams.append("&");
                    sbParams.append("isMapView").append("=").append(mapInfo.getIsMapView());

                    //URL url = new URL("http://169.254.92.16:8080/MapProject/Edit");
                    URL url = new URL("http://192.168.11.13:8080/MapProject/Edit");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept-Charset", "UTF-8");
                    con.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

                    String strParams = sbParams.toString();
                    OutputStream os = con.getOutputStream();
                    os.write(strParams.getBytes("UTF-8"));
                    os.flush();
                    os.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                    String line;
                    String page = "";

                    while ((line = reader.readLine()) != null) {
                        page += line;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();

    }

    public void deleteAndroidList(final int id) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    StringBuffer sbParams = new StringBuffer();

                    sbParams.append("id").append("=").append(id);

                    //URL url = new URL("http://169.254.92.16:8080/MapProject/Delete");
                    URL url = new URL("http://192.168.11.13:8080/MapProject/Delete");
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();

                    con.setRequestMethod("POST");
                    con.setRequestProperty("Accept-Charset", "UTF-8");
                    con.setRequestProperty("Context_Type", "application/x-www-form-urlencoded;cahrset=UTF-8");

                    String strParams = sbParams.toString();
                    OutputStream os = con.getOutputStream();
                    os.write(strParams.getBytes("UTF-8"));
                    os.flush();
                    os.close();

                    BufferedReader reader = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));

                    String line;
                    String page = "";

                    while ((line = reader.readLine()) != null) {
                        page += line;
                    }
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        }).start();
    }

    // InputStream -> String
    static String InputStreamToString(InputStream is) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line;
        while ((line = br.readLine()) != null) {
            sb.append(line);
        }
        br.close();
        return sb.toString();
    }
}
