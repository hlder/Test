package com.hld.mvvm.common

import android.os.Bundle
import androidx.fragment.app.Fragment

interface ARouterController{
    fun getFragment(bundle: Bundle?):Fragment?
}