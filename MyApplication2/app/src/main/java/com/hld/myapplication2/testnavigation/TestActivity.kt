package com.hld.myapplication2.testnavigation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.hld.myapplication2.R
import com.hld.myapplication2.databinding.ActivitiyTestBinding

class TestActivity: AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        DataBindingUtil.setContentView<ActivitiyTestBinding>(this, R.layout.activitiy_test)
    }
}