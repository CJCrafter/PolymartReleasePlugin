plugins {
    `java-gradle-plugin`
    id("com.gradle.plugin-publish") version "1.2.1"
    id("org.jetbrains.kotlin.jvm") version "1.5.31"
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(platform("org.jetbrains.kotlin:kotlin-bom"))
    implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
    implementation("com.squareup.okhttp3:okhttp:4.9.1")
}

version = "1.0.0"
group = "com.cjcrafter"

gradlePlugin {
    plugins {
        create("polymartRelease") {
            id = "com.cjcrafter.polymart-release"
            implementationClass = "com.cjcrafter.polymartrelease.PolymartReleasePlugin"
            displayName = "Polymart Release Plugin"
            description = "Upload releases to polymart.org"
        }
    }
}

pluginBundle {
    website = "https://github.com/CJCrafter/PolymartReleasePlugin"
    vcsUrl = "https://github.com/CJCrafter/PolymartReleasePlugin.git"
    description = "Upload releases to polymart.org"
    tags = listOf("polymart", "minecraft")
}
