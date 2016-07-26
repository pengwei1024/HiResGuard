package com.apkfuns.hiresguard;

import org.gradle.api.Plugin
import org.gradle.api.Project

/**
 * Created by pengwei on 16/7/26.
 */
public class HiResGuardPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        project.apply plugin: 'osdetector'
        project.extensions.create('resGuardOption', HiResGuardExtension)
        project.extensions.add("sevenzip", new ExecutorExtension("sevenzip"))
        project.tasks.create('resGuardTask', HiResGuardTask)
        project.afterEvaluate {
            def ExecutorExtension sevenzip = project.extensions.findByName("sevenzip")
            sevenzip.loadArtifact(project)
        }
    }
}
