package wangyeo.interview.qualgoo.routes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import wangyeo.interview.qualgoo.BaseAppState
import wangyeo.interview.qualgoo.splash.SplashScreen
import wangyeo.interview.qualgoo.ui.screens.HomeScreen

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.splash(appState: BaseAppState) {
    navigation(
        route = NestedGraph.SPLASH.route,
        startDestination = Screen.Splash.route,
    ) {
        composable(
            route = Screen.Splash.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            SplashScreen(appState)
        }
    }
}

@OptIn(ExperimentalAnimationApi::class)
fun NavGraphBuilder.home(appState: BaseAppState) {
    navigation(
        route = NestedGraph.HOME.route,
        startDestination = Screen.Home.route,
    ) {
        composable(
            route = Screen.Home.route,
            enterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
            },
            exitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
            },
            popEnterTransition = {
                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
            },
            popExitTransition = {
                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
            },
        ) {
            HomeScreen(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }

//        composable(
//            route = Screen.SevenDaysWeather.route,
//            enterTransition = {
//                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
//            },
//            exitTransition = {
//                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Left, animationSpec = tween(700))
//            },
//            popEnterTransition = {
//                slideIntoContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
//            },
//            popExitTransition = {
//                slideOutOfContainer(AnimatedContentTransitionScope.SlideDirection.Right, animationSpec = tween(700))
//            },
//        ) {
//            SevenDaysWeather(
//                appState = appState,
//                viewModel = hiltViewModel(),
//            )
//        }
    }
}