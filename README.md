# Polymart Release Gradle Plugin

A Gradle plugin for automating releases to Polymart.

## Features

- Upload new versions of your resource directly from your Gradle build script.
- Supports multiple file types including `.zip`, `.jar`, `.yml`, `.sk`, and `.schematic`.
- Option to mark a version as beta or snapshot.

## Installation

Add the following code to your `build.gradle` (Groovy) or `build.gradle.kts` (Kotlin) file to include the plugin:

### Groovy

```groovy
plugins {
    id 'com.cjcrafter.polymart-release' version '1.0.1'
}
```

### Kotlin

```kotlin
plugins {
    id("com.cjcrafter.polymart-release") version "1.0.1"
}
```

## Configuration

You can configure the plugin by adding a `polymart` block to your `build.gradle` or `build.gradle.kts`.

### Groovy

```groovy
polymart {
    apiKey = 'your_api_key_here'
    resourceId = 1234
    version = '1.0.0'
    title = 'New Release'
    message = 'New features added'
    file = file('path/to/your/file.zip')
    beta = false  // optional, defaults to false
    snapshot = false  // optional, defaults to false
}
```

### Kotlin

```kotlin
polymart {
    apiKey = "your_api_key_here"
    resourceId = 1234
    version = "1.0.0"
    title = "New Release"
    message = "New features added"
    file.set(file("path/to/your/file.zip"))
    beta = false  // optional, defaults to false
    snapshot = false  // optional, defaults to false
}
```

## Usage

Once configured, you can run the following Gradle task to upload a new version to Polymart:

```bash
./gradlew createPolymartRelease
```

## License

MIT License

## Contributing

Contributions are welcome. Please submit a pull request or create an issue for any enhancements or bug fixes.
