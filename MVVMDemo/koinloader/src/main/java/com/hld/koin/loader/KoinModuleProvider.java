package com.hld.koin.loader;

import org.koin.core.module.Module;

import java.util.List;

public interface KoinModuleProvider {
    List<Module> getModules();
}
