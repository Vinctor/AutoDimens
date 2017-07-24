package com.vinctor.autodimens

import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class PluginImpl implements Plugin<Project> {
    void apply(Project project) {
        AutoExtension extension = project.extensions.create('auto', AutoExtension.class)
        PropertyFinder propertyFinder = new PropertyFinder(project, extension)

        Task autoDimensTask = project.tasks.create("autoDimens", AutoDimensTask) {
            it.projectFile = project.projectDir
            it.finder = propertyFinder
        }
    }
}