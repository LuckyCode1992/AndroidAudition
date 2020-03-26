package com.example.androidaudition.broadcastReceive

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log

//静态注册
class BroadCastReceiver1:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("receiver_", "静态注册：context:${context?.packageName}")
    }

}
//动态注册
class BroadCastReceiver2:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("receiver_", "动态注册：context:${context?.packageName}")
    }

}
// app内广播
class BroadCastReceiver3:BroadcastReceiver(){
    override fun onReceive(context: Context?, intent: Intent?) {
        Log.d("receiver_", "app内广播：context:${context?.packageName}")
    }
}