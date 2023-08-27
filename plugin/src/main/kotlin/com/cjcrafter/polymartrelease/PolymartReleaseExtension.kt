package com.cjcrafter.polymartrelease

import org.gradle.api.Project
import org.gradle.api.file.RegularFileProperty

open class PolymartReleaseExtension(project: Project) {
    var apiKey: String? = null
    var resourceId: Int? = null
    var version: String? = null
    var title: String? = null
    var message: String? = null
    var file: RegularFileProperty = project.objects.fileProperty()
    var beta: Boolean? = null
    var snapshot: Boolean? = null
}
