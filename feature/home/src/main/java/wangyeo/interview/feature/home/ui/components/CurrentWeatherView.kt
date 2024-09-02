package wangyeo.interview.feature.home.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.PullRefreshState
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import wangyeo.interview.core.enums.ActionType
import wangyeo.interview.feature.common.components.AppScaffold
import wangyeo.interview.feature.home.viewmodel.HomeViewState

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CurrentWeatherView(
    state: HomeViewState,
    pullRefreshState: PullRefreshState,
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    onDrawer: () -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
    onShowSnackBar: (message: String) -> Unit = {},
    onNavigateSearch: () -> Unit = {},
    onErrorPositiveAction: (action: ActionType?, value: Any?) -> Unit = { _, _ -> },
    onGoToBook: () -> Unit,
) {
    AppScaffold(
        modifier = Modifier.fillMaxSize(),
        state = state,
        snackbarHostState = snackBarHostState,
        onDismissErrorDialog = onDismissErrorDialog,
        onShowSnackbar = onShowSnackBar,
        onErrorPositiveAction = onErrorPositiveAction,
        topBar = {
            CurrentWeatherAppBar(
                modifier = Modifier.statusBarsPadding(),
                city = state.currentWeather?.city,
                onDrawer = onDrawer,
                onNavigateSearch = onNavigateSearch,
            )
        },
    ) { _, viewState ->
        Box(Modifier.pullRefresh(pullRefreshState)) {
            viewState.currentWeather?.let {
                CurrentWeatherContent(
                    currentWeather = it,
                    listHourly = viewState.listHourlyWeatherToday,
                    onGoToBook = onGoToBook,
                )
            }

            PullRefreshIndicator(
                modifier = Modifier.align(Alignment.TopCenter),
                refreshing = state.isRefresh,
                state = pullRefreshState
            )
        }
    }
}