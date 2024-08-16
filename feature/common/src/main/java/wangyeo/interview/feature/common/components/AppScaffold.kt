package wangyeo.interview.feature.common.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.contentColorFor
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import wangyeo.interview.feature.common.base.ViewState
import wangyeo.interview.core.enums.ActionType

@Composable
fun <VS : ViewState> AppScaffold(
    modifier: Modifier = Modifier,
    state: VS,
    snackbarHostState: SnackbarHostState = SnackbarHostState(),
    topBar: @Composable () -> Unit = {},
    bottomBar: @Composable () -> Unit = {},
    floatingActionButton: @Composable () -> Unit = {},
    floatingActionButtonPosition: FabPosition = FabPosition.End,
    containerColor: Color = MaterialTheme.colorScheme.background,
    contentColor: Color = contentColorFor(containerColor),
    onShowSnackbar: (message: String) -> Unit = {},
    onErrorPositiveAction: (action: ActionType?, value: Any?) -> Unit = { _, _ -> },
    onErrorNegativeAction: (action: ActionType?, value: Any?) -> Unit = { _, _ -> },
    onDismissErrorDialog: () -> Unit,
    content: @Composable (PaddingValues, VS) -> Unit,
) {
    Scaffold(
        modifier = modifier.fillMaxSize(),
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
        topBar = topBar,
        bottomBar = bottomBar,
        snackbarHost = {
            SnackbarHost(
                hostState = snackbarHostState,
                modifier = Modifier.navigationBarsPadding(),
            )
        },
        floatingActionButton = floatingActionButton,
        floatingActionButtonPosition = floatingActionButtonPosition,
        containerColor = containerColor,
        contentColor = contentColor,
    ) { paddingValues ->
        AppHandleError(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            state = state,
            onShowSnackBar = onShowSnackbar,
            onPositiveAction = onErrorPositiveAction,
            onNegativeAction = onErrorNegativeAction,
            onDismissErrorDialog = onDismissErrorDialog,
        ) { viewState ->
            content.invoke(paddingValues, viewState)
        }
    }
}
