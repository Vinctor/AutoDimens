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
        getIntArray(project, 'standard', extension.standard)
    }

    def getExtraDimens() {
        getList(project, 'extraDimens', extension.extraDimens)
    }

    private int getInteger(Project project, String propertyName, int defaultValue) {
        project.hasProperty(propertyName) ? Integer.parseInt(project.getProperty(propertyName)) : defaultValue
    }

    private List getList(Project project, String propertyName, defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private int[] getIntArray(Project project, String propertyName, defaultValue) {
        project.hasProperty(propertyName) ? project.getProperty(propertyName) : defaultValue
    }

    private boolean getBoolean(Project project, String propertyName, boolean defaultValue) {
        project.hasProperty(propertyName) ? Boolean.parseBoolean(project.getProperty(propertyName)) : defaultValue
    }

}
