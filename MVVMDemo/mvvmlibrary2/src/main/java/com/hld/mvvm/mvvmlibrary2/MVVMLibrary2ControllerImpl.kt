package com.hld.mvvm.mvvmlibrary2

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hld.mvvm.mvvmlibrary.api2.MVVMLibrary2Controller
import com.hld.mvvm.mvvmlibrary2.fragment.Library2ActivityViewModel
import com.hld.mvvm.mvvmlibrary2.fragment.Library2Fragment

class MVVMLibrary2ControllerImpl : MVVMLibrary2Controller {
    override fun changeTestValue(activity: FragmentActivity,value:String) {
        activity.viewModels<Library2ActivityViewModel>().value.testValueAct.value = value
    }

    override fun getFragment(bundle: Bundle?): Fragment {
        return Library2Fragment().apply {
            arguments = bundle
        }
    }
}