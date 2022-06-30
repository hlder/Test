package com.hld.mvvm.koinloader_demo;

import com.hld.koin.loader.KoinModule;
import com.hld.koin.loader.KoinModuleProvider;

import java.util.List;

@KoinModule
public class TestKoinLoader implements KoinModuleProvider {
    @Override
    public List<org.koin.core.module.Module> getModules() {
        return null;
    }
}
