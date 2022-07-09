package com.hld.mvvm.koinloader_demo

import com.hld.koin.loader.KoinModule
import com.hld.koin.loader.KoinModuleProvider
import org.koin.core.module.Module
import org.koin.dsl.module

@KoinModule
class TestKoinLoader2 : KoinModuleProvider {
    override fun getModules(): List<Module> {
        return listOf(
            module {
                factory { TestDemoBean2() }
            }
        )
    }
}