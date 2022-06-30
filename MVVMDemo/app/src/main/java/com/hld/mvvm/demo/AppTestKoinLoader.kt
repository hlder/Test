package com.hld.mvvm.demo

import com.hld.koin.loader.KoinModule
import com.hld.koin.loader.KoinModuleProvider

import org.koin.core.module.Module

@KoinModule
class AppTestKoinLoader:KoinModuleProvider {
    override fun getModules(): MutableList<Module>? {
        return null
    }
}