package wangyeo.interview.qualgoo.routes

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.core.tween
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.google.accompanist.navigation.animation.composable
import wangyeo.interview.feature.common.global.Constants
import wangyeo.interview.qualgoo.BaseAppState
import wangyeo.interview.qualgoo.ui.screens.SplashScreen
import wangyeo.interview.qualgoo.ui.screens.HomeScreen
import wangyeo.interview.qualgoo.ui.screens.SearchByTextScreen

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

        composable(
            route = "${Screen.SearchByText.route}?lat={${Constants.Key.LAT}}&lng={${Constants.Key.LNG}}&from_route={${Constants.Key.FROM_ROUTE}}",
            arguments = listOf(
                navArgument(Constants.Key.LAT) { type = NavType.StringType },
                navArgument(Constants.Key.LNG) { type = NavType.StringType },
                navArgument(Constants.Key.FROM_ROUTE) { type = NavType.StringType },
            ),
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
            SearchByTextScreen(
                appState = appState,
                viewModel = hiltViewModel(),
            )
        }
    }
}