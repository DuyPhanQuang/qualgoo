plugins {
    `kotlin-dsl`
}

gradlePlugin {
    plugins {
        register("AndroidCoreLibraryPlugin") {
            id = "android.core.library.plugin"
            implementationClass = "commons.AndroidCoreLibraryPlugin"
        }
    }
}

repositories {
    gradlePluginPortal()
    google()
    mavenCentral()

    val storageUrl = System.getenv("FLUTTER_STORAGE_BASE_URL") ?: "https://storage.googleapis.com"
    maven {
        setUrl("../lib/build/host/outputs/repo")
    }
    maven {
        setUrl("$storageUrl/download.flutter.io")
    }
}

dependencies {
    implementation("com.android.tools.build:gradle:8.3.2")
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.9.20")
    implementation("com.google.dagger:hilt-android-gradle-plugin:2.48.1")
    implementation("com.google.firebase.crashlytics:com.google.firebase.crashlytics.gradle.plugin:2.9.4")
    implementation("com.google.android.libraries.mapsplatform.secrets-gradle-plugin:secrets-gradle-plugin:2.0.1")
}