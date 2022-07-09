package com.hld.mvvm.koinloader_demo

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class KoinLoaderDemoActivity : AppCompatActivity(),KoinComponent {
    private val test1 by inject<TestDemoBean>()
    private val test2 by inject<TestDemoBean2>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_koin_loader_demo)
        findViewById<Button>(R.id.button1).setOnClickListener {
            println("=========koinLoaderDemo test1:${test1.a()}")
        }
        findViewById<Button>(R.id.button2).setOnClickListener {
            println("=========koinLoaderDemo test2:${test2.a()}")
        }

    }
}