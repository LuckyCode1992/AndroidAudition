package com.example.androidaudition.demoactivity;

import android.content.res.Configuration;
import android.os.PersistableBundle;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.example.androidaudition.R;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data_demo);
        Log.d("activity_","B - onCreate");
        findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.putExtra("result", "我是返回结果");
                setResult(RESULT_OK, intent);
                finish();
            }
        });
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        Log.d("activity_","B - onConfigurationChanged");
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Log.d("activity_","B - onSaveInstanceState1");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d("activity_","B - onStart");
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("activity_","B - onResume");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d("activity_","B - onPause");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d("activity_","B - onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("activity_","B - onDestroy");
    }
}
