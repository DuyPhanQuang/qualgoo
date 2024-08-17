plugins {
    id("plugin.android-common")
}


dependencies {
    CORE
    DATA
    DOMAIN
    COMMON_THEME
    COMMON_COMPOSABLE
    FEATURE_COMMON
}
android {
    namespace = "wangyeo.interview.feature.search"
}
