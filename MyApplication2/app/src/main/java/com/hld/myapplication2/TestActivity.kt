package com.hld.myapplication2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Button(this).apply {
            text="跳转"
            setOnClickListener {
                startActivity(Intent(this@TestActivity,TestActivity2::class.java))
            }
        })
    }
}