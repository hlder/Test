package com.hld.mvvm.common

import org.koin.core.module.Module

interface ModuleController {
    fun getModules():List<Module>
}