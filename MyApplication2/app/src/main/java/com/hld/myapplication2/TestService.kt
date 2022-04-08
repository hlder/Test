package com.hld.myapplication2

import android.app.Service
import android.content.Intent
import android.os.IBinder


class TestService : Service() {

    override fun onBind(intent: Intent?): IBinder? {
//        return object: Test.Stub() {
//            override fun doTest(String: Int) {
//                println("======serviceDoTest")
//            }
//        }
        return null
    }

}