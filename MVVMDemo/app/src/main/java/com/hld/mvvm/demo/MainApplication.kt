package com.hld.mvvm.demo

import android.app.Application
import com.alibaba.android.arouter.launcher.ARouter
import com.hld.koin.loader.api.KoinModuleInit
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import org.koin.core.module.Module

class MainApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        ARouter.openLog()     // 打印日志
        ARouter.openDebug()   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)

        ARouter.init(this)

        startKoin {
        }
        val modules:List<Module> = KoinModuleInit.getListModules(this@MainApplication)
        loadKoinModules(modules)
    }
}