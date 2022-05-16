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

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        val fragment = TestFragment1()
        val args = Bundle()
        args.putString("name", "张三")
        args.putInt("id", 1)
        fragment.arguments = args
        fragmentTransaction.add(R.id.transition_layout_save, fragment)
        fragmentTransaction.commitAllowingStateLoss()

    }
}