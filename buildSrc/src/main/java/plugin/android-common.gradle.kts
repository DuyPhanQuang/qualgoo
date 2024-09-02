package plugin

import AppConfig
import baseDependencies
import composeDependencies
import testDependencies


plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.plugin.parcelize")
    id("com.google.dagger.hilt.android")
    kotlin("kapt")
}

android {
    compileSdk = AppConfig.compileSdk
    defaultConfig {
        minSdk = AppConfig.minSdk
        targetSdk = AppConfig.targetSdk
        consumerProguardFiles ("consumer-rules.pro")

        ndk {
            // Filter for architectures supported by Flutter
            abiFilters += listOf("armeabi-v7a", "arm64-v8a", "x86_64")
        }
    }

    buildTypes {
        testBuildType = "debug"
        debug {}
        release {}

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
        resources.excludes.apply {
            add("META-INF/AL2.0")
            add("META-INF/LGPL2.1")
        }
    }

    libraryVariants.all {
        kotlin.sourceSets {
            getByName(name) {
                kotlin.srcDir(File("build/generated/kapt/$name/kotlin"))
            }
        }
    }
}
kapt {
    correctErrorTypes = true
}
dependencies {
    baseDependencies()
    composeDependencies()
    testDependencies()

    implementation(kotlin("stdlib"))
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))


    debugImplementation("com.example.channel_flutter:flutter_debug:1.0")
    releaseImplementation("com.example.channel_flutter:flutter_release:1.0")
    add("profileImplementation", "com.example.channel_flutter:flutter_profile:1.0")


    // Add Flutter dependency
    implementation(project(":demo_channel_flutter"))
}




