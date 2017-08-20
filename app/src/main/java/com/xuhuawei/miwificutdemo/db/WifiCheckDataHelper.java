package com.xuhuawei.miwificutdemo.db;


/**
 * Created by Administrator on 2017/6/20 0020.
 */
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class WifiCheckDataHelper extends SQLiteOpenHelper implements IWIFITablColumns{

    private static final String CREATTABLE = "CREATE TABLE " + TABLE_NAME
            + " (" + _ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + WIFI_NAME+ " VARCHAR(200), "
            + WIFI_DATE + " LONG, "
            + WIFI_STATUS + " INTEGER, "
            + WIFI_TYPE + " INTEGER"
            +")";
    public WifiCheckDataHelper(Context context) {
        super(context, SQLITE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATTABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        int version = oldVersion;
        if (version != DATABASE_VERSION) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
