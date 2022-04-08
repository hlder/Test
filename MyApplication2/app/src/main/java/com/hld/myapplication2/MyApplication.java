package com.hld.myapplication2;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import com.alibaba.android.arouter.launcher.ARouter;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        Executor executor= Executors.newSingleThreadExecutor();
//        Executors.newFixedThreadPool();
//        Executors.newCachedThreadPool();
//        Executors.newSingleThreadExecutor()


        Handler handler=new Handler(Looper.getMainLooper());
        handler.obtainMessage();


        Testk.INSTANCE.a();

        ARouter.openLog();     // 打印日志
        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this); // 尽可能早，推荐在Application中初始化

    }
}
