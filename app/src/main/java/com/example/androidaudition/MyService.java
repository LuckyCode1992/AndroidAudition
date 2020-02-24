package com.example.androidaudition;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyService extends Service {

    @Override
    public void onCreate() {
        super.onCreate();
        Log.d("service_", "onCreate");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.d("service_", "onStartCommand");
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d("service_", "onDestroy");
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("service_", "onBind");
        MyBinder binder = new MyBinder();
        binder.mProcessId = 111;
        return binder;
    }
}
