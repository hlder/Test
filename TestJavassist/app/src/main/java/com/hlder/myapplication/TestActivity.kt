package com.hlder.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.hlder.mylibrary.TestStatic

class TestActivity : AppCompatActivity() {
    private lateinit var testZoomView: TestZoomView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)
        testZoomView = findViewById(R.id.testZoomView)


        testZoomView.setOnClickListener {
            testZoomView.scaleX = testZoomView.scaleX + 1f
            testZoomView.scaleY = testZoomView.scaleY + 1f

            testZoomView.translationX = testZoomView.translationX + 1f
            testZoomView.translationY = testZoomView.translationY + 1f
        }

        findViewById<TextView>(R.id.textView).text = MainActivity.str

        findViewById<Button>(R.id.button1).setOnClickListener {
            println("---------------button1点击--------------------")
            TestStatic.init()
            val cls = Class.forName("com.test.TestStatic")
            cls.getMethod("a").invoke(cls)
        }
    }
}