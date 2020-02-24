package com.example.androidaudition;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

import androidx.annotation.Nullable;

public class MyIntentService extends IntentService {

    public MyIntentService() {
        super("hello");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        Log.d("service_","onHandleIntent");
    }
}
