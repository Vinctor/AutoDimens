package com.vinctor.autodimens

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
        Task autoDimensTask = project.tasks.create("autoDimens", AutoDimensTask) {
            it.projectFile = project.projectDir
        }
        project.task("asss") {
            println "hello"
        }
    }
}