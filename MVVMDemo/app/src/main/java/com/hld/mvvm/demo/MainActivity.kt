package com.hld.mvvm.demo

import android.database.ContentObserver
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.provider.MediaStore
import android.util.Log
import com.hld.mvvm.home.HomeFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().add(R.id.frameLayout,HomeFragment()).commitAllowingStateLoss()

        contentResolver.registerContentObserver(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
            false,object: ContentObserver(Handler(Looper.getMainLooper())) {
                override fun onChange(selfChange: Boolean) {
                    super.onChange(selfChange)
                    Log.d("dddd","==================1")
                }
                override fun onChange(selfChange: Boolean, uri: Uri?) {
                    super.onChange(selfChange, uri)
                    Log.d("dddd","==================2")
                }
                override fun onChange(selfChange: Boolean, uri: Uri?, flags: Int) {
                    super.onChange(selfChange, uri, flags)
                    Log.d("dddd","==================3")
                }
                override fun onChange(
                    selfChange: Boolean,
                    uris: MutableCollection<Uri>,
                    flags: Int
                ) {
                    super.onChange(selfChange, uris, flags)
                    Log.d("dddd","==================4")
                }
        })

        Log.d("dddd","==================start")
        Thread{
            fun run(){
                var i=0
                while (true){
                    Thread.sleep(1000)
                    Log.d("dddd","------i:${i++}")
                }
            }
        }.start()
    }


}