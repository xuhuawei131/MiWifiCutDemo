package com.xuhuawei.miwificutdemo.services;

import android.app.Notification;
import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.text.TextUtils;
import android.util.Log;

import com.xuhuawei.miwificutdemo.R;
import com.xuhuawei.miwificutdemo.bean.NotificationBean;
import com.xuhuawei.miwificutdemo.bean.WifiInfoBean;
import com.xuhuawei.miwificutdemo.constant.Const;
import com.xuhuawei.miwificutdemo.db.WIFICheckDao;
import com.xuhuawei.miwificutdemo.utils.DateUtils;
import com.xuhuawei.miwificutdemo.utils.MyNotificationManager;
import com.xuhuawei.miwificutdemo.utils.NetStatusUtils;

public class CheckService extends Service {
    private long currentTime = 0;

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        NotificationBean bean = new NotificationBean();
        bean.content = this.getString(R.string.notification_content);
        bean.tickerContent = this.getString(R.string.ticker_content);
        bean.title = this.getString(R.string.notification_title);
        Notification notification = MyNotificationManager.getInstance().getNotification(this, bean);

        startForeground(R.string.app_name, notification);

//        this.registerReceiver(netChangeReceiver);
        IntentFilter filter = new IntentFilter();
        filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(netChangeReceiver, filter);
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        unregisterReceiver(netChangeReceiver);
    }

    private BroadcastReceiver netChangeReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
//            NetStatusUtils.isNetworkConnected(context);

            long tempTime = System.currentTimeMillis();
            if (tempTime - currentTime < 500) {
                return;
            }
            currentTime = tempTime;

            NetStatusUtils.NetWorkType network_type = NetStatusUtils.getNetWorkState(context);
            StringBuilder sb = new StringBuilder();
            WifiInfoBean wifiBean = null;
            if (network_type == NetStatusUtils.NetWorkType.NETWORK_WIFI) {//连接上新的wifi
                sb.append("WIFI连上了");
                WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
                WifiInfo wifiInfo = wifiManager.getConnectionInfo();
                wifiBean = new WifiInfoBean();
                wifiBean.wifiName = wifiInfo.getSSID();// 连接的wifi名字
                wifiBean.date = System.currentTimeMillis();
                wifiBean.status = 1;
            } else if (network_type == NetStatusUtils.NetWorkType.NETWORK_NONE) {//无网络
                sb.append("WIFI断开了");
                wifiBean = new WifiInfoBean();
                WifiInfoBean bean = WIFICheckDao.getInstance().getLastConnectWifiName();
                wifiBean.wifiName = bean == null ? "未知" : bean.wifiName;
                wifiBean.date = System.currentTimeMillis();
                wifiBean.status = 0;
            } else if (network_type == NetStatusUtils.NetWorkType.NETWORK_MOBILE) {//移动网络

            } else {

            }
            if (wifiBean != null) {
                WIFICheckDao.getInstance().insertWifiInfo(wifiBean);

                sb.append("-").append(DateUtils.getDateFormat());
                NotificationBean bean = new NotificationBean();
                bean.title = context.getString(R.string.notification_change_net_title);
                bean.content = sb.toString();
                bean.tickerContent = context.getString(R.string.ticker_content);
                MyNotificationManager.getInstance().showNotification(context, bean);

                LocalBroadcastManager.getInstance(context).sendBroadcast(new Intent(Const.ACTION_UPDATE_ITEM));
            }
        }
    };

}
