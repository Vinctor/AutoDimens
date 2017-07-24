package com.vinctor.autodimens

import org.gradle.api.Project;

class PropertyFinder {

    private final Project project
    private final AutoExtension extension

    PropertyFinder(Project project, AutoExtension extension) {
        this.extension = extension
        this.project = project
    }

    def getMaxPx() {
        getInteger(project, 'maxPx', extension.maxPx)
    }

    def getStandard() {
        getList(project, 'standard', extension.standard)
    }

    def getExtraDimens() {
        getIntList(project, 'extra', extension.extraDimens)
    }

    def getUseDeviceSize() {
        getBoolean(project, 'useDeviceSize', extension.useDeviceSize)
    }

    private int getInteger(Project project, String propertyName, int defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    def getIntList(Project project, String propertyName, defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private ArrayList getList(Project project, String propertyName, defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private boolean getBoolean(Project project, String propertyName, boolean defaultValue) {
        project.hasProperty(propertyName) ? Boolean.valueOf(project.getProperty(propertyName)) : defaultValue
    }

}
