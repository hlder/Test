package com.hld.koin.loader.api;

import android.content.Context;
import android.util.Log;

import com.hld.koin.loader.KoinLoaderConstant;

import org.koin.core.module.Module;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

import dalvik.system.DexFile;

public class KoinModuleInit {
    private static List<Module> listModules = new LinkedList<>();

    public static List<Module> getListModules(Context context) {
        if (listModules.size() <= 0) {
            long startTime = Calendar.getInstance().getTimeInMillis();
            searchPresenters(context);
            long endTime = Calendar.getInstance().getTimeInMillis();
            System.out.println("总时间searchTime:" + (endTime - startTime) + "ms");
        }
        return listModules;
    }

    private static void searchPresenters(Context context) {
        ClassLoader classLoader = context.getClassLoader();
        String packageCodePath = context.getPackageCodePath();

        File file = new File(packageCodePath);
        File fileD = file.getParentFile();

        if (fileD != null && fileD.isDirectory()) {
            File[] files = fileD.listFiles();
            if (files == null) {
                return;
            }
            for (File itemFile : files) {
                if (itemFile.getName().endsWith(".apk")) {
                    loadApkFile(itemFile, classLoader);
                }
            }
        }
    }

    private static void loadApkFile(File file, ClassLoader classLoader) {
        try {
            DexFile df = new DexFile(file);
            for (Enumeration iter = df.entries(); iter.hasMoreElements(); ) {
                String clsName = "" + iter.nextElement();
                if (clsName.startsWith(KoinLoaderConstant.KOIN_LOADER_PROVIDER_PACKAGE_NAME)) {
                    System.out.println("load module:" + clsName);
                    try {
                        long startTime = Calendar.getInstance().getTimeInMillis();
                        Class<?> cls = classLoader.loadClass(clsName);
                        Method method = cls.getMethod("getModules");
                        List<Module> list = (List<Module>) method.invoke(cls.newInstance());
                        long endTime = Calendar.getInstance().getTimeInMillis();
                        System.out.println("加载一个:" + (endTime - startTime) + "ms");
                        listModules.addAll(list);
                    } catch (ClassNotFoundException | NoSuchMethodException | InvocationTargetException | IllegalAccessException | InstantiationException e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
