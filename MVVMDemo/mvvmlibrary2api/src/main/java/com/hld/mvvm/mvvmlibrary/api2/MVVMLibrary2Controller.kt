package com.hld.mvvm.mvvmlibrary.api2

import androidx.fragment.app.FragmentActivity
import com.hld.mvvm.common.ARouterController

interface MVVMLibrary2Controller : ARouterController {
    fun changeTestValue(activity: FragmentActivity, value: String)
}