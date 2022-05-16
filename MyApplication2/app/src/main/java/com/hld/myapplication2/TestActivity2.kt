package com.hld.myapplication2

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class TestActivity2: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(Button(this).apply {
            text="Toast并返回"
            setOnClickListener {
                Toast.makeText(this@TestActivity2,"aaaaaa",Toast.LENGTH_SHORT).show()
                finish()
            }
        })
    }
}