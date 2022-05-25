package com.hld.mvvm.demo

import android.content.Context
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class TestActivity2:AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        getSystemService(Context.CONNECTIVITY_SERVICE)
//        Context.ACTIVITY_SERVICE
//        Context.ACCESSIBILITY_SERVICE
//        Context.ALARM_SERVICE
//        Context.ACCOUNT_SERVICE
//        Context.BATTERY_SERVICE
//        Context.APPWIDGET_SERVICE
//        Context.APP_OPS_SERVICE
//        Context.AUDIO_SERVICE
//        Context.BLUETOOTH_SERVICE
//        Context.CAMERA_SERVICE
//
//        getSystemService(Context.BATTERY_SERVICE)
//        getSystemService(Context.APPWIDGET_SERVICE)
//        getSystemService(Context.APP_OPS_SERVICE)
//        getSystemService(Context.AUDIO_SERVICE)
//        getSystemService(Context.BLUETOOTH_SERVICE)
//        getSystemService(Context.CAMERA_SERVICE)
    }
}