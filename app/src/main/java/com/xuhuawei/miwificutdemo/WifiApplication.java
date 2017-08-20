package com.xuhuawei.miwificutdemo;

import android.app.Application;
import android.content.Context;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class WifiApplication extends Application {
    public static Context CONTEXT;
    @Override
    public void onCreate() {
        super.onCreate();
        CONTEXT=this;
    }
}
