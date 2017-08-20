package com.xuhuawei.miwificutdemo.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v4.app.NotificationCompat;

import com.xuhuawei.miwificutdemo.R;
import com.xuhuawei.miwificutdemo.activity.RecoderListActivity;
import com.xuhuawei.miwificutdemo.bean.NotificationBean;

/**
 * Created by Administrator on 2017/6/20 0020.
 */

public class MyNotificationManager {
    private static final MyNotificationManager ourInstance = new MyNotificationManager();

    public static MyNotificationManager getInstance() {
        return ourInstance;
    }

    private MyNotificationManager() {
    }

    public void showNotification(Context context,NotificationBean bean) {
        Notification mNotification=getNotification(context,bean);

        NotificationManager mNotificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        mNotificationManager.cancel(R.string.ticker_content);
        mNotificationManager.notify(R.string.ticker_content, mNotification);
    }

    public Notification getNotification(Context context, NotificationBean bean) {
        PendingIntent mPendingIntent = null; // 延时意图

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context);

        builder.setDefaults(Notification.DEFAULT_ALL);
        Vibrator vib = (Vibrator) context.getSystemService(Service.VIBRATOR_SERVICE);
        vib.vibrate(1000);

        Intent notificationIntent = new Intent();
        notificationIntent.setClass(context,RecoderListActivity.class);


        mPendingIntent = PendingIntent.getActivity(context, R.string.app_name, notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setSmallIcon(R.mipmap.ic_launcher_round)
                .setTicker(bean.tickerContent)
                .setContentIntent(mPendingIntent)
                .setContentTitle(bean.title)
                .setContentText(bean.content);

        Notification mNotification = builder.build();
        mNotification.flags |= Notification.FLAG_AUTO_CANCEL;
        return mNotification;
    }
}
