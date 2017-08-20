package com.xuhuawei.miwificutdemo.db;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.xuhuawei.miwificutdemo.WifiApplication;
import com.xuhuawei.miwificutdemo.bean.WifiInfoBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class WIFICheckDao implements IWIFITablColumns {
    private static WIFICheckDao instance;
    private WifiCheckDataHelper helper;

    private WIFICheckDao() {
        helper = new WifiCheckDataHelper(WifiApplication.CONTEXT);
    }

    public static WIFICheckDao getInstance() {
        if (instance == null) {
            instance = new WIFICheckDao();
        }
        return instance;
    }

    private SQLiteDatabase getDBHelper() {
        return helper.getWritableDatabase();
    }

    public long insertWifiInfo(WifiInfoBean bean) {
        SQLiteDatabase db = getDBHelper();
        ContentValues values = new ContentValues();
        values.put(WIFI_NAME, bean.wifiName);
        values.put(WIFI_STATUS, bean.status);
        values.put(WIFI_DATE, bean.date);
        values.put(WIFI_TYPE, bean.type);
        return db.insert(TABLE_NAME, null, values);
    }

    /**
     * 获取所有wifi信息
     * @return
     */
    public List<WifiInfoBean> getAllWifiInfo() {
        String sql = "SELECT " + _ID + "," + WIFI_NAME + "," + WIFI_STATUS + ","
                + WIFI_DATE + "," + WIFI_TYPE + " FROM " + TABLE_NAME
                + " ORDER BY " + WIFI_DATE + " DESC ";
        SQLiteDatabase db = getDBHelper();
        Cursor cursor = db.rawQuery(sql, null);
        List<WifiInfoBean> arrayList = new ArrayList<>();
        if (cursor != null) {
            while (cursor.moveToNext()) {
                WifiInfoBean info = new WifiInfoBean();
                info.id = cursor.getInt(cursor.getColumnIndex(_ID));
                info.date = cursor.getLong(cursor.getColumnIndex(WIFI_DATE));
                info.wifiName = cursor.getString(cursor.getColumnIndex(WIFI_NAME));
                info.status = cursor.getInt(cursor.getColumnIndex(WIFI_STATUS));
                info.type = cursor.getInt(cursor.getColumnIndex(WIFI_TYPE));
                arrayList.add(info);
            }
            cursor.close();
        }
        return arrayList;
    }


    /**
     * 清空所有的数据
     * @return
     */
    public int clearAllData() {
        SQLiteDatabase db = getDBHelper();
        return db.delete(TABLE_NAME, null,null);
    }

    /**
     * 获取上一个连接的wifi名字
     * @return
     */
    public WifiInfoBean getLastConnectWifiName() {
        String sql = "SELECT " + _ID + "," + WIFI_NAME + "," + WIFI_STATUS + ","
                + WIFI_DATE + "," + WIFI_TYPE + " FROM " + TABLE_NAME + " WHERE " + WIFI_STATUS
                + "=1  ORDER BY " + WIFI_DATE + " DESC ," + _ID + " DESC LIMIT 1 OFFSET 0";
        SQLiteDatabase db = getDBHelper();
        Cursor cursor = db.rawQuery(sql, null);
        WifiInfoBean info = null;
        if (cursor != null && cursor.moveToNext()) {
            info = new WifiInfoBean();
            info.id = cursor.getInt(cursor.getColumnIndex(_ID));
            info.date = cursor.getLong(cursor.getColumnIndex(WIFI_DATE));
            info.wifiName = cursor.getString(cursor.getColumnIndex(WIFI_NAME));
            info.status = cursor.getInt(cursor.getColumnIndex(WIFI_STATUS));
            info.type = cursor.getInt(cursor.getColumnIndex(WIFI_TYPE));
            cursor.close();
        }
        return info;
    }
}
