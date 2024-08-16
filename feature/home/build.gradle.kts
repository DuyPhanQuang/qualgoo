plugins {
    id("plugin.android-common")
}


dependencies {
    implementation(project(":app"))
    CORE
    DATA
    DOMAIN
    COMMON_THEME
    COMMON_COMPOSABLE
    FEATURE_COMMON
}