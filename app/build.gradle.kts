import java.util.Properties

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")

    // GG PlayServices, firebase, maps
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

val properties = Properties().apply {
    load(project.rootProject.file("local.properties").inputStream())
}

android {
    namespace = "wangyeo.interview.qualgoo"
    compileSdk = AppConfig.compileSdk

    defaultConfig {
        applicationId = "wangyeo.interview.qualgoo"
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        versionCode = AppConfig.versionCode
        versionName = AppConfig.VersionName
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }

        ndk {
            // Filter for architectures supported by Flutter
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86_64")
        }

//        buildConfigField("String", "BASE_URL", properties.getProperty("BASE_URL"))
    }

    buildTypes {
        getByName("debug") {
            isDebuggable = true
            isMinifyEnabled = false
            initWith(getByName("debug"))
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
                "coroutines.pro",
                "gson.pro",
                "okhttp3.pro",
                "firebase-crashlytics.pro"
            )
        }
        getByName("release") {
            isDebuggable = false
            isMinifyEnabled = true
            initWith(getByName("release"))
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
                "retrofit2.pro",
                "coroutines.pro",
                "gson.pro",
                "okhttp3.pro",
                "firebase-crashlytics.pro"
            )
        }

        create("profile") {
            initWith(getByName("debug"))
        }
    }

    configurations {
        getByName("profileImplementation") {
        }
    }

    testOptions {
        unitTests {
            isIncludeAndroidResources = true
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
        freeCompilerArgs = freeCompilerArgs.toMutableList().apply {
            add("-opt-in=kotlin.RequiresOptIn")
        }
    }

    buildFeatures {
        compose = true
    }

    composeOptions {
        kotlinCompilerExtensionVersion = AppConfig.KotlinCompilerExtension
    }

    packaging  {
        resources.excludes.add("META-INF/**/*")
    }

    repositories{
        val storageUrl = System.getenv("FLUTTER_STORAGE_BASE_URL") ?: "https://storage.googleapis.com"
        repositories {
            maven {
                setUrl("../lib/build/host/outputs/repo")
            }
            maven {
                setUrl("$storageUrl/download.flutter.io")
            }
        }
    }
}

dependencies {
    baseDependencies()
    composeDependencies()
    moduleDependencies()
    testDependencies()

    debugImplementation("com.example.book:flutter_debug:1.0")
    releaseImplementation("com.example.book:flutter_release:1.0")
    add("profileImplementation", "com.example.book:flutter_profile:1.0")
}