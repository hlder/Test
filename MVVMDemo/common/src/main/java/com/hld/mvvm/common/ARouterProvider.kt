package com.hld.mvvm.common

import com.alibaba.android.arouter.facade.template.IProvider

interface ARouterProvider : IProvider {
    fun getController(): ARouterController
    fun getModuleController(): ModuleController
}