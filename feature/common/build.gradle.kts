plugins {
    id("plugin.android-common")
}


dependencies {
    CORE
    DATA
    DOMAIN
    COMMON_THEME
    COMMON_COMPOSABLE
}
android {
    namespace = "wangyeo.interview.feature.common"
}
