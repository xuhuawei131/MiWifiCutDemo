package com.xuhuawei.miwificutdemo.activity;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.xuhuawei.miwificutdemo.R;
import com.xuhuawei.miwificutdemo.services.CheckService;

public class MainActivity extends AppCompatActivity {
    private static final String PERMISSIONS[] = {
            "android.permission.ACCESS_NETWORK_STATE",
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ActivityCompat.requestPermissions(this,PERMISSIONS,200);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Intent intent = new Intent(this, CheckService.class);
        startService(intent);
    }
}
