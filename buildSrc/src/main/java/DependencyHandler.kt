import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler

fun DependencyHandler.implementation(dependencyNotation: Any): Dependency? =
    add("implementation", dependencyNotation)

fun DependencyHandler.testImplementation(dependencyNotation: Any): Dependency? =
    add("testImplementation", dependencyNotation)

fun DependencyHandler.androidTestImplementation(dependencyNotation: Any): Dependency? =
    add("androidTestImplementation", dependencyNotation)

fun DependencyHandler.kapt(dependencyNotation: Any): Dependency? =
    add("kapt", dependencyNotation)

fun DependencyHandler.composeDependencies() {
    implementation(platform(Libraries.Compose.composeBom))
    implementation(Libraries.Compose.composeActivity)
    implementation(Libraries.Compose.composeUi)
    implementation(Libraries.Compose.composeUiToolingPreview)
    implementation(Libraries.Compose.composeUiUtil)
    implementation(Libraries.Compose.composeFoundation)
    implementation(Libraries.Compose.composeRuntime)
    implementation(Libraries.Compose.composeMaterial3)

    //navigation
    implementation(Libraries.Navigation.navigationCompose)

    //coil
    implementation(Libraries.Coil.coilCompose)

    //hilt navigation
    implementation(Libraries.Hilt.hiltNavigationCompose)

    //accompanist
    accompanistDependencies()

    //constraint layout
    implementation(Libraries.Compose.constraintLayoutCompose)

    //timber
    implementation(Libraries.Timber.timber)

    //okhttp
    implementation(Libraries.SquareUp.okhttp3)
    implementation(Libraries.SquareUp.okhttp3LoggingInterceptor)

    //retrofit
    implementation(Libraries.SquareUp.retrofit2)
    implementation(Libraries.SquareUp.converterGson)

    //coroutines
    implementation(Libraries.Coroutine.android)
    implementation(Libraries.Coroutine.core)

    //kotlinreflect
    implementation(Libraries.KotlinReflect.reflect)

    //Firebase
    implementation(platform(Libraries.Google.Firebase.bom))
    implementation(Libraries.Google.Firebase.crashlytics)

    // Maps, Places, PlayServices
    implementation(Libraries.Google.PlayServices.map)
    implementation(Libraries.Google.PlayServices.location)
    implementation(Libraries.Google.maps)
    implementation(Libraries.Google.places)
}

fun DependencyHandler.accompanistDependencies() {
    implementation(Libraries.Accompanist.pager)
    implementation(Libraries.Accompanist.swiperefresh)
    implementation(Libraries.Accompanist.indicators)
    implementation(Libraries.Accompanist.systemuicontroller)
    implementation(Libraries.Accompanist.navigationMaterial)
    implementation(Libraries.Accompanist.navigationAnimation)
    implementation(Libraries.Accompanist.permission)
}

fun DependencyHandler.baseDependencies() {
//    implementation(Libraries.AndroidX.appCompat)
    implementation(Libraries.AndroidX.coreKtx)
    //Lifecycle
    implementation(Libraries.AndroidX.lifecycleRunTimeKtx)
    implementation(Libraries.AndroidX.lifecycleRunTimeCompose)

    //ViewModel
    implementation(Libraries.AndroidX.viewModelKtx)
    implementation(Libraries.AndroidX.viewModelCompose)

    //LiveData
    implementation(Libraries.AndroidX.liveData)

    //WorkManager
    implementation(Libraries.AndroidX.workManager)

    //Lottie
    implementation(Libraries.Lottie.lottie)

    implementation(Libraries.AndroidX.splashScreen)
    implementation(Libraries.Google.gson)
    implementation(Libraries.Hilt.hiltAndroid)
    implementation(Libraries.Hilt.hiltWork)
    kapt(Libraries.Hilt.hiltAndroidCompiler)
    implementation(Libraries.Google.guava)
}

fun DependencyHandler.testDependencies() {
    androidTestImplementation(Libraries.Test.testCoreKtx)
    androidTestImplementation(Libraries.Test.espressorCore)
    androidTestImplementation(Libraries.Test.runner)
    androidTestImplementation(Libraries.Test.junitExtKtx)
    androidTestImplementation(Libraries.Test.truthExt)

    //Coroutine
    implementation(Libraries.Coroutine.test)
}

fun DependencyHandler.moduleDependencies() {
    CORE
    DATA
    DOMAIN
    COMMON_THEME
    COMMON_COMPOSABLE
    FEATURE_HOME
    FEATURE_COMMON
}

val DependencyHandler.CORE
    get() = implementation(project(mapOf("path" to ":core")))

val DependencyHandler.COMMON_THEME
    get() = implementation(project(mapOf("path" to ":common:theme")))

val DependencyHandler.COMMON_COMPOSABLE
    get() = implementation(project(mapOf("path" to ":common:composable")))

val DependencyHandler.DATA
    get() = implementation(project(mapOf("path" to ":data")))

val DependencyHandler.DOMAIN
    get() = implementation(project(mapOf("path" to ":domain")))

val DependencyHandler.FEATURE_HOME
    get() = implementation(project(mapOf("path" to ":feature:home")))

val DependencyHandler.FEATURE_COMMON
    get() = implementation(project(mapOf("path" to ":feature:common")))