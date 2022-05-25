package com.hld.mvvm.mvvmlibrary2

import android.content.Context
import com.alibaba.android.arouter.facade.annotation.Route
import com.hld.mvvm.common.ARouterController
import com.hld.mvvm.common.ARouterPathConfig
import com.hld.mvvm.common.ARouterProvider
import com.hld.mvvm.common.ModuleController

@Route(path = ARouterPathConfig.PROVIDER_PATH_MVVM_LIBRARY2)
class MVVMLibrary2ARouterProviderImpl : ARouterProvider {
    override fun getController(): ARouterController {
        return MVVMLibrary2ControllerImpl()
    }

    override fun getModuleController(): ModuleController {
        return MVVMLibrary2ModuleControllerImpl()
    }

    override fun init(context: Context?) {
    }
}