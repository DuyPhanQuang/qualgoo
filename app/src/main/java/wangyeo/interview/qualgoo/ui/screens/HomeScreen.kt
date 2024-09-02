package wangyeo.interview.qualgoo.ui.screens

import android.Manifest
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.collectLatest
import wangyeo.interview.core.enums.ActionType
import wangyeo.interview.feature.common.global.Constants
import wangyeo.interview.feature.home.ui.components.CurrentWeatherView
import wangyeo.interview.feature.home.viewmodel.HomeViewModel
import wangyeo.interview.qualgoo.BaseAppState
import wangyeo.interview.qualgoo.routes.Screen

@OptIn(ExperimentalPermissionsApi::class, ExperimentalMaterialApi::class)
@Composable
fun HomeScreen(
    appState: BaseAppState,
    viewModel: HomeViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    val pullRefreshState = rememberPullRefreshState(
        refreshing = state.isRefresh,
        onRefresh = {
            viewModel.onRefreshCurrentWeather()
        },
    )

    val context = LocalContext.current

    val locationPermissionState = rememberMultiplePermissionsState(
        listOf(
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
        )
    ) { permissions ->
        when {
            permissions.all { it.value } -> viewModel.getCurrentLocation()
            else -> viewModel.permissionIsNotGranted()
        }
    }

    // Get data with location
    LaunchedEffect(true) {
        appState.getDataFromNextScreen(Constants.Key.LAT_LNG, Constants.Default.LAT_LNG_DEFAULT)?.collect {
            if (it != LatLng(0.0, 0.0)) {
                viewModel.getWeatherByLocation(it)
                appState.removeDataFromNextScreen<LatLng>(Constants.Key.LAT_LNG)
            }
        }
    }

    // Get data from back after selected address
    LaunchedEffect(true) {
        appState.getDataFromNextScreen(Constants.Key.ADDRESS_NAME, Constants.Default.ADDRESS_NAME_DEFAULT)?.collect {
            if (it.isNotBlank()) {
                viewModel.getWeatherByAddressName(addressName = it)
                appState.removeDataFromNextScreen<LatLng>(Constants.Key.ADDRESS_NAME)
            }
        }
    }

    // Locale change
    LaunchedEffect(true) {
        appState.localChange.collectLatest {
            viewModel.onRefreshCurrentWeather(false)
        }
    }

    // Get event
    LaunchedEffect(state) {
        val navigateToSearch = state.navigateSearch
        val requestPermission = state.isRequestPermission

        when {
            requestPermission -> {
                when {
                    locationPermissionState.allPermissionsGranted -> {
                        viewModel.getCurrentLocation()
                    }

                    locationPermissionState.shouldShowRationale -> {
                        viewModel.permissionIsNotGranted()
                    }

                    else -> {
                        locationPermissionState.launchMultiplePermissionRequest()
                    }
                }
            }

            navigateToSearch != null -> {
                appState.navigateToSearchByText(Screen.Home, navigateToSearch)
            }

            else -> return@LaunchedEffect
        }
        viewModel.cleanEvent()
    }

    CurrentWeatherView(
        state = state,
        pullRefreshState = pullRefreshState,
        snackBarHostState = appState.snackBarHost,
        onDrawer = {
            appState.openDrawer()
        },
        onShowSnackBar = {
            appState.showSnackBar(it)
        },
        onDismissErrorDialog = {
            viewModel.hideError()
        },
        onNavigateSearch = {
            viewModel.navigateToSearchByText()
        },
        onErrorPositiveAction = { action, _ ->
            action?.let {
                when (it) {
                    ActionType.RETRY_API -> {
                        viewModel.retry()
                    }

                    ActionType.OPEN_PERMISSION -> {
                        appState.openAppSetting(context)
                    }
                }
            }
        },
        onGoToFlutterScreen = {
            viewModel.goToFlutterActivity()
        }
    )
}