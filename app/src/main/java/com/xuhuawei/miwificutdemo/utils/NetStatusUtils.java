package com.xuhuawei.miwificutdemo.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import static com.xuhuawei.miwificutdemo.utils.NetStatusUtils.NetWorkType.NETWORK_MOBILE;
import static com.xuhuawei.miwificutdemo.utils.NetStatusUtils.NetWorkType.NETWORK_NONE;
import static com.xuhuawei.miwificutdemo.utils.NetStatusUtils.NetWorkType.NETWORK_WIFI;

/**
 * 网络状态utils
 * Created by xuhuawei on 2017/6/20 0020.
 */

public class NetStatusUtils {
    public enum NetWorkType {
        NETWORK_NONE,//没有连接网络
        NETWORK_MOBILE,//移动网络
        NETWORK_WIFI//无线网络
    }

    /**
     * 获取当前网络状态
     *
     * @param context
     * @return
     */
    public static NetWorkType getNetWorkState(Context context) {
        // 得到连接管理器对象
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        if (activeNetworkInfo != null && activeNetworkInfo.isConnected()) {
            if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_WIFI)) {
                return NETWORK_WIFI;
            } else if (activeNetworkInfo.getType() == (ConnectivityManager.TYPE_MOBILE)) {
                return NETWORK_MOBILE;
            }
        } else {
            return NETWORK_NONE;
        }
        return NETWORK_NONE;
    }

    /**
     * 网络是否已经连接
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null && mNetworkInfo.isAvailable()) {
                return mNetworkInfo.isConnected();
            }
        }
        return false;
    }

}
