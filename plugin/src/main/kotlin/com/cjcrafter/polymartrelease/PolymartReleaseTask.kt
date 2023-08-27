package com.cjcrafter.polymartrelease

import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody.Companion.asRequestBody
import org.gradle.api.file.RegularFileProperty
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.Optional
import org.gradle.api.tasks.OutputFile
import org.gradle.api.tasks.TaskAction
import java.io.IOException

open class PolymartReleaseTask : org.gradle.api.DefaultTask() {

    @get:Input
    var apiKey: String? = null

    @get:Input
    var resourceId: String? = null

    @get:Input
    var version: String? = null

    @get:Input
    var title: String? = null

    @get:Input
    var message: String? = null

    @get:OutputFile
    var file: RegularFileProperty? = null

    @Input
    @Optional
    var beta: Boolean? = null

    @Input
    @Optional
    var snapshot: Boolean? = null

    init {
        val extension = project.extensions.findByType(PolymartReleaseExtension::class.java)
        apiKey = extension?.apiKey
        resourceId = extension?.resourceId?.toString()
        version = extension?.version
        title = extension?.title
        message = extension?.message
        file = extension?.file
        beta = extension?.beta
        snapshot = extension?.snapshot
    }

    @TaskAction
    fun upload() {
        log("Starting release of version $version")
        val client = OkHttpClient()

        val actualFile = file!!.get().asFile
        val fileExtension = actualFile.extension

        // Lookup media type or use a generic fallback
        val mediaType = extensionToMediaType[fileExtension] ?: "application/octet-stream"
        log("Found file extension $fileExtension, using $mediaType")

        val requestBody = MultipartBody.Builder()
            .setType(MultipartBody.FORM)
            .addFormDataPart("api_key", apiKey!!)
            .addFormDataPart("resource_id", resourceId!!)
            .addFormDataPart("version", version!!)
            .addFormDataPart("title", title!!)
            .addFormDataPart("message", message!!)
            .addFormDataPart("file", actualFile.name, actualFile.asRequestBody(mediaType.toMediaTypeOrNull()))
            .apply { beta?.let { addFormDataPart("beta", (if (beta!!) 1 else 0).toString()) } }
            .apply { snapshot?.let { addFormDataPart("snapshot", (if (snapshot!!) 1 else 0).toString()) } }
            .build()

        val request = Request.Builder()
            .url("https://api.polymart.org/v1/postUpdate")
            .post(requestBody)
            .build()

        client.newCall(request).execute().use { response ->
            if (!response.isSuccessful) {
                throw IOException("Unexpected code $response")
            }
            println(response.body?.string())
        }
        log("Done")
    }

    companion object {
        // Mapping of file extensions to media types
        private val extensionToMediaType = mapOf(
            "jar" to "application/java-archive",
            "zip" to "application/zip",
            "yml" to "application/x-yaml",
            "sk" to "text/plain",  // Skript file, which is often text-based
            "schematic" to "application/octet-stream"  // Assuming this is a binary file
        )

        private fun log(msg: String) {
            println("polymartRelease: $msg")
        }
    }
}