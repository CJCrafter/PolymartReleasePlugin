package com.cjcrafter.polymartrelease

import org.gradle.api.Plugin
import org.gradle.api.Project

// The main plugin class
class PolymartReleasePlugin : Plugin<Project> {
    override fun apply(project: Project) {
        project.extensions.create("polymart", PolymartReleaseExtension::class.java)
        project.tasks.register("createPolymartRelease", PolymartReleaseTask::class.java)
    }
}