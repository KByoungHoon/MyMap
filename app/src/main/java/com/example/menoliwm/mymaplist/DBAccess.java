package com.example.menoliwm.mymaplist;

import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Menoliwm on 2017-06-06.
 */

public class DBAccess extends SQLiteOpenHelper {

    static String DATABASE_PATH = "/data/data/com.example.menoliwm.mymaplist/databases/";
    static final String DATABASE_NAME = "MyMap.db";
    private final Context mContext;
    SQLiteDatabase myDataBase;

    public DBAccess(Context context, int version) {
        super(context, DATABASE_NAME, null, version);
        this.mContext = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void createDataBase() throws IOException {
        boolean mDataBaseExist = checkDataBase();
        if (!mDataBaseExist) {
            this.getReadableDatabase();
            try {
                copyDataBase();
            } catch (IOException mIOException) {
                mIOException.printStackTrace();
                throw new Error("Error copying database");
            } finally {
                this.close();
            }
        }
    }

    private void copyDataBase() throws IOException{
        try {
            InputStream dbInput = mContext.getAssets().open(DATABASE_NAME);
            File outFileName = mContext.getDatabasePath(DATABASE_NAME);
            OutputStream dbOutput = new FileOutputStream(outFileName);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = dbInput.read(buffer)) > 0) {
                dbOutput.write(buffer, 0, length);
            }
            dbOutput.flush();
            dbOutput.close();
            dbInput.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private boolean checkDataBase() {
        try {
            final String mPath = DATABASE_PATH + DATABASE_NAME;
            final File file = new File(mPath);
            if (file.exists())
                return true;
            else
                return false;
        } catch (SQLiteException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean openDataBase() throws SQLException {
        String mPath = DATABASE_PATH + DATABASE_NAME;
        myDataBase = SQLiteDatabase.openDatabase(mPath, null,
                SQLiteDatabase.OPEN_READWRITE);
        return myDataBase.isOpen();
    }

    @Override
    public synchronized void close() {
        if (myDataBase != null)
            myDataBase.close();
        SQLiteDatabase.releaseMemory();
        super.close();
    }

    public List getMapListDB() {
        myDataBase = getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM mapinfo", null);
        List list = new ArrayList();
        MapInfo mapInfo = new MapInfo();

        try {
            while (cursor.moveToNext()) {
                mapInfo.setId(0);
                mapInfo.setUserid(cursor.getString(1));
                mapInfo.setTitle(cursor.getString(2));
                mapInfo.setAddress(cursor.getString(3));
                mapInfo.setLatitude(cursor.getDouble(4));
                mapInfo.setLongitude(cursor.getDouble(5));
                mapInfo.setIsMapView(cursor.getWantsAllOnMoveCalls());
                list.add(mapInfo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

        }
        return list;
    }

    public MapInfo getSeletMap(int id){
        myDataBase = getReadableDatabase();
        Cursor cursor = myDataBase.rawQuery("SELECT * FROM mapinfo where id=?", null);
        MapInfo mapInfo = new MapInfo();

        try{
            while (cursor.moveToNext()) {
                mapInfo.setId(0);
                mapInfo.setUserid(cursor.getString(1));
                mapInfo.setTitle(cursor.getString(2));
                mapInfo.setAddress(cursor.getString(3));
                mapInfo.setLatitude(cursor.getDouble(4));
                mapInfo.setLongitude(cursor.getDouble(5));
                mapInfo.setIsMapView(cursor.getWantsAllOnMoveCalls());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return mapInfo;
    }

    public void insertDB(MapInfo mapInfo) {
        myDataBase = getWritableDatabase();
        String sql = "insert into mapinfo(id, userid, title, address, latitude, longitude, isMapView) values(?,?,?,?,?,?,?)";
        mapInfo = new MapInfo();
        try {
            String[] bindArgs = {String.valueOf(mapInfo.getId()), mapInfo.getUserid(), mapInfo.getTitle(), mapInfo.getAddress(), String.valueOf(mapInfo.getLatitude()), String.valueOf(mapInfo.getLongitude()), String.valueOf(mapInfo.getIsMapView())};
            myDataBase.execSQL(sql, bindArgs);
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public void updateDB(MapInfo mapInfo) {
        myDataBase = getWritableDatabase();
        String sql="update mapinfo set userid=?, title=?, address=?, latitude=?, longitude=?, isMapView=? where id=?";
        try {
            String[] bindArgs = {mapInfo.getUserid(), mapInfo.getTitle(), mapInfo.getAddress(), String.valueOf(mapInfo.getLatitude()), String.valueOf(mapInfo.getLongitude()), String.valueOf(mapInfo.getIsMapView()), String.valueOf(mapInfo.getId())};
            myDataBase.execSQL(sql, bindArgs);
            myDataBase.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
        }
    }

    public boolean deleteDB(int id){
        myDataBase = getWritableDatabase();
        String sql="delete from mapinfo where id=?";
        String[] bindArgs = {String.valueOf(id)};
        try{
            myDataBase.execSQL(sql, bindArgs);
        }catch (Exception e){
            e.printStackTrace();
        }finally {
        }
        return true;
    }
}
