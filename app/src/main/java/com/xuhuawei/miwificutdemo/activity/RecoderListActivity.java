package com.xuhuawei.miwificutdemo.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.Toast;

import com.xuhuawei.miwificutdemo.R;
import com.xuhuawei.miwificutdemo.adapter.WifiRecoderAdapter;
import com.xuhuawei.miwificutdemo.bean.WifiInfoBean;
import com.xuhuawei.miwificutdemo.compare.RecoderCompare;
import com.xuhuawei.miwificutdemo.constant.Const;
import com.xuhuawei.miwificutdemo.db.WIFICheckDao;
import com.xuhuawei.miwificutdemo.services.CheckService;
import com.xuhuawei.miwificutdemo.utils.DateUtils;
import com.xuhuawei.miwificutdemo.utils.FileUtils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RecoderListActivity extends AppCompatActivity implements View.OnClickListener {

    private ListView listview;
    private WifiRecoderAdapter adapter;
    private List<WifiInfoBean> arrayList = new ArrayList<>();
    private RecoderCompare compare;
    private static final String PERMISSIONS[] = {
            "android.permission.ACCESS_NETWORK_STATE",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recoder_list);


        compare = new RecoderCompare();

        View btn_clear = findViewById(R.id.btn_clear);
        View btn_export = findViewById(R.id.btn_export);

        btn_clear.setOnClickListener(this);
        btn_export.setOnClickListener(this);

        listview = (ListView) findViewById(R.id.listview);
        adapter = new WifiRecoderAdapter(this, arrayList);
        listview.setAdapter(adapter);
        refreshData();


        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Const.ACTION_UPDATE_ITEM);
        LocalBroadcastManager.getInstance(this).registerReceiver(receiver, intentFilter);

        ActivityCompat.requestPermissions(this, PERMISSIONS, 200);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LocalBroadcastManager.getInstance(this).unregisterReceiver(receiver);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(this, CheckService.class);
        startService(intent);
    }

    private void refreshData() {
        List<WifiInfoBean> dbList = WIFICheckDao.getInstance().getAllWifiInfo();
        arrayList.clear();
        arrayList.addAll(dbList);

        Collections.sort(arrayList, compare);
        adapter.notifyDataSetChanged();
    }

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            refreshData();
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_clear:
                WIFICheckDao.getInstance().clearAllData();
                refreshData();
                break;
            case R.id.btn_export:
                exportAllData2SD();
                break;
        }
    }

    private void exportAllData2SD() {
        File file = FileUtils.getRootFile("wifiResult.txt");
        StringBuilder sb = new StringBuilder();
        for (WifiInfoBean bean : arrayList) {
            String item = bean.status == 1 ? "连接" : "断开";
            sb.append(getIndex(bean.id))
                    .append(" | ")
                    .append(bean.wifiName)
                    .append(" | ")
                    .append(item)
                    .append(" | ")
                    .append(DateUtils.getDateFormat(bean.date))
                    .append(" \r\n");
        }
        FileUtils.writeSD(sb.toString(), file);
        Toast.makeText(this, file.getAbsolutePath() + ",导出完毕!", Toast.LENGTH_LONG).show();
    }

    private String getIndex(int index) {
        if (index > 100) {
            return "" + index;
        } else if (index > 9) {
            return "0" + index;
        } else {
            return "00" + index;
        }
    }
}
