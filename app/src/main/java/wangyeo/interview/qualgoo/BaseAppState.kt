package wangyeo.interview.qualgoo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.text.intl.Locale
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import wangyeo.interview.core.utils.openAppSettings
import wangyeo.interview.qualgoo.routes.DrawerTab
import wangyeo.interview.qualgoo.routes.Screen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun rememberBaseAppState(
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
    controller: NavHostController = rememberAnimatedNavController(),
    drawer: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    snackbarHost: SnackbarHostState = remember { SnackbarHostState() },
): BaseAppState = remember(coroutineScope, controller, drawer, snackbarHost) {
    BaseAppState(coroutineScope, controller, drawer, snackbarHost)
}

class BaseAppState(
    private val coroutineScope: CoroutineScope,
    val controller: NavHostController,
    val drawer: DrawerState,
    val snackBarHost: SnackbarHostState,
) {
    private val drawerTabs = DrawerTab.values()

    val shouldEnableGesture: Boolean
        @Composable get() = controller.currentBackStackEntryAsState().value?.destination?.route in drawerTabs.map { it.route }

    val drawerItemSelected: DrawerTab
        @Composable get() {
            val route = controller.currentBackStackEntryAsState().value?.destination?.route
            return drawerTabs.firstOrNull { it.route == route } ?: DrawerTab.HOME
        }

    val isCustomDarkMode: Boolean
        get() {
            val listScreenCustomizeDarkMode = listOf(Screen.SearchByMap.route)
            val route = controller.currentBackStackEntry?.destination?.route
            return route in listScreenCustomizeDarkMode
        }

    private val _localeChange = Channel<Locale>()
    val localChange: Flow<Locale> = _localeChange.receiveAsFlow()

    fun currentDestinationIs(route: String): Boolean = controller.currentBackStackEntry?.destination?.route == route

    fun <T> getDataFromNextScreen(key: String, defaultValue: T): StateFlow<T>? =
        controller.currentBackStackEntry?.savedStateHandle?.getStateFlow(key, defaultValue)

    fun <T> removeDataFromNextScreen(key: String) {
        controller.currentBackStackEntry?.savedStateHandle?.remove<T>(key)
    }

    fun onLocaleChange(locale: Locale) {
        coroutineScope.launch {
            _localeChange.send(locale)
        }
    }

    fun openDrawer() {
        coroutineScope.launch {
            drawer.open()
        }
    }

    fun closeDrawer() {
        coroutineScope.launch {
            drawer.close()
        }
    }

    fun showSnackBar(message: String) {
        coroutineScope.launch {
            snackBarHost.showSnackbar(message)
        }
    }

    fun popBackStack(popToRoute: String? = null, params: Map<String, Any>? = null) {
        if (popToRoute == null) {
            params?.forEach { data ->
                controller.previousBackStackEntry?.savedStateHandle?.set(data.key, data.value)
            }
            controller.popBackStack()
        } else {
            params?.forEach { data ->
                controller.getBackStackEntry(popToRoute).savedStateHandle[data.key] = data.value
            }
            controller.popBackStack(route = popToRoute, inclusive = false)
        }
    }

    fun navigateToHome() {
        closeDrawer()
        controller.navigate(route = Screen.Home.route) {
            popUpTo(Screen.Splash.route) {
                inclusive = true
            }
        }
    }

    fun navigateToCurrentWeather() {
        closeDrawer()

        controller.navigate(route = Screen.Home.route) {
            popUpTo(Screen.Home.route) {
                inclusive = true
            }
        }
    }

    fun navigateToSevenDaysWeather() {
        closeDrawer()

        controller.navigate(route = Screen.SevenDaysWeather.route)
    }

    fun openAppSetting(context: Context) {
        context.openAppSettings()
    }

}