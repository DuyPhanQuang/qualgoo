package wangyeo.interview.qualgoo

import android.annotation.SuppressLint
import android.content.Intent
import android.content.IntentFilter
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import com.google.accompanist.navigation.animation.AnimatedNavHost
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import wangyeo.interview.feature.common.broadcast.LocaleChangeBroadcast
import wangyeo.interview.qualgoo.components.AppDrawerContent
import wangyeo.interview.qualgoo.routes.NestedGraph
import wangyeo.interview.qualgoo.routes.Screen
import wangyeo.interview.qualgoo.routes.home
import wangyeo.interview.qualgoo.routes.splash

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun RootApp(
    appState: BaseAppState = rememberBaseAppState(),
    channelFlutterViewModel: ChannelFlutterViewModel
) {
    val systemUiController = rememberSystemUiController()
    val darkIcons = isSystemInDarkTheme()

    val localFocusManager = LocalFocusManager.current
    val localContext = LocalContext.current

    SideEffect {
        systemUiController.setSystemBarsColor(color = Color.Transparent, darkIcons = !darkIcons)
    }

    DisposableEffect(true) {
        val broadcast = LocaleChangeBroadcast(appState::onLocaleChange)

        localContext.registerReceiver(broadcast, IntentFilter(Intent.ACTION_LOCALE_CHANGED))

        onDispose {
            localContext.unregisterReceiver(broadcast)
        }
    }

    ModalNavigationDrawer(
        drawerState = appState.drawer,
        modifier = Modifier.pointerInput(Unit) {
            detectTapGestures {
                localFocusManager.clearFocus()
            }
        },
        gesturesEnabled = appState.shouldEnableGesture,
        drawerContent = {
            ModalDrawerSheet {
                AppDrawerContent(
                    selectedItem = appState.drawerItemSelected,
                    onClickCurrentWeather = {
                        if (!appState.currentDestinationIs(Screen.Home.route)) {
                            appState.navigateToCurrentWeather()
                        } else {
                            appState.closeDrawer()
                        }
                    },
                )
            }
        },
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize(),
            containerColor = Color.Transparent,
            contentWindowInsets = WindowInsets(0, 0, 0, 0),
        ) {
            AnimatedNavHost(
                navController = appState.controller,
                startDestination = NestedGraph.SPLASH.route,
            ) {
                splash(appState = appState)

                home(
                    appState = appState,
                    channelFlutterViewModel = channelFlutterViewModel
                )
            }
        }
    }

}