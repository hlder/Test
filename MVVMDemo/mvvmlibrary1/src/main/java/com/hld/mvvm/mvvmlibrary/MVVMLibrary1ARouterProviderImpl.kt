package com.hld.mvvm.mvvmlibrary

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hld.mvvm.common.ARouterController
import com.hld.mvvm.common.ARouterPathConfig
import com.hld.mvvm.common.ARouterProvider
import com.hld.mvvm.common.ModuleController

@Route(path = ARouterPathConfig.PROVIDER_PATH_MVVM_LIBRARY1)
class MVVMLibrary1ARouterProviderImpl : ARouterProvider {
    override fun getController(): ARouterController {
        return MVVMLibrary1ControllerImpl()
    }

    override fun getModuleController(): ModuleController {
        return MVVMLibrary1ModuleControllerImpl()
    }

    override fun init(context: Context?) {
    }
}