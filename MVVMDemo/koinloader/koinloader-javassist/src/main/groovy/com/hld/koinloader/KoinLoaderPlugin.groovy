package com.hld.koinloader

import org.gradle.api.Plugin
import org.gradle.api.Project;

/**
 * koinLoader的插件
 */
class KoinLoaderPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        // 注册transform
        project.project.android.registerTransform(new KoinLoaderTransform(project))
    }
}
