package com.plugin

import com.android.build.gradle.AppExtension
import org.gradle.api.Plugin
import org.gradle.api.Project

class CustomizePlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        println("CustomizePlugin apply()")

        def appExtension = project.extensions.getByType(AppExtension)
        // 区别是工程还是依赖库种
//        LibraryExtension libraryExtension = project.extensions.getByType(LibraryExtension)

        // 使用扩展参数
        project.extensions.create('asmConfig', ASMConfig)

        appExtension.registerTransform(new ASMTransform())
    }
}