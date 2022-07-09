package com.hld.mvvm.demo

import com.hld.koin.loader.KoinModule
import com.hld.koin.loader.KoinModuleProvider

import org.koin.core.module.Module
import org.koin.dsl.module

@KoinModule
class AppTestKoinLoader:KoinModuleProvider {
    override fun getModules(): List<Module> {
        return mutableListOf(
            module {
                factory { TestBean() }
            }
        )
    }
}