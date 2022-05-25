package com.hld.mvvm.mvvmlibrary

import android.os.Bundle
import androidx.activity.viewModels
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import com.hld.mvvm.mvvmlibrary.api.MVVMLibrary1Controller
import com.hld.mvvm.mvvmlibrary.fragment.Library1Fragment

class MVVMLibrary1ControllerImpl : MVVMLibrary1Controller {

    override fun getFragment(bundle: Bundle?): Fragment {
        return Library1Fragment()
    }
}