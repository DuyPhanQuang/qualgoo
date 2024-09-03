pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven(url = "https://oss.sonatype.org/content/repositories/snapshots/")

        val flutterStorageUrl = System.getenv("FLUTTER_STORAGE_BASE_URL") ?: "https://storage.googleapis.com"
        maven(url = "E:\\qualgoo\\lib\\build\\host\\outputs\\repo")
        maven(url = "$flutterStorageUrl/download.flutter.io")
    }
}

rootProject.name = "qualgoo app"
include(":app")
include(":core")
include(":common:theme")
include(":common:composable")
include(":data")
include(":domain")
include(":feature:home")
include(":feature:common")
include(":feature:search")