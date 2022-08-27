package com.javassist

import com.android.build.gradle.AppPlugin
import org.gradle.api.Plugin
import org.gradle.api.Project

class TestPlugin implements Plugin<Project> {
    @Override
    void apply(Project project) {
        boolean isApp = project.plugins.withType(AppPlugin)
        println("=============TestPlugin2=========project:" + project.name + "  isApp:" + isApp)
        project.project.android.registerTransform(new TestTransform(project))
    }
}