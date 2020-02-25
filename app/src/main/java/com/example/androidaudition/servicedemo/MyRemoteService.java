package com.example.androidaudition.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyRemoteService extends Service {

    IMyAidlInterfaceImp myAidlInterfaceImp = new IMyAidlInterfaceImp();
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        Log.d("service_","MyRemoteService:onBind:"+"threadName:"+Thread.currentThread().getName());
        return myAidlInterfaceImp;
    }
}
