package wangyeo.interview.qualgoo.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.search.ui.components.text.SearchByTextView
import com.example.search.viewmodel.SearchByTextViewModel
import wangyeo.interview.core.extensions.getSystemLocale
import wangyeo.interview.feature.common.global.Constants
import wangyeo.interview.qualgoo.BaseAppState
import wangyeo.interview.qualgoo.R

@Composable
fun SearchByTextScreen(
    appState: BaseAppState,
    viewModel: SearchByTextViewModel = viewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    var text by rememberSaveable {
        mutableStateOf("")
    }

    // Get event
    LaunchedEffect(state) {
        val navigateToSearchByMap = state.navigateToSearchByMap

        when {
            navigateToSearchByMap != null -> {
                //todo: navigate gg maps search
            }

            else -> return@LaunchedEffect
        }

        viewModel.cleanEvent()
    }

    // Hide keyboard when SearchByText is disposed
    val keyboardController = LocalSoftwareKeyboardController.current

    DisposableEffect(true) {
        onDispose {
            keyboardController?.hide()
        }
    }

    // Change the placeholder text when switch to vi or en
    val context = LocalContext.current
    val locale = remember {
        context.getSystemLocale()
    }

    LaunchedEffect(locale) {
        viewModel.updatePlaceHolder(context.getString(R.string.search_every_where_you_want))
    }

    SearchByTextView(
        state = state,
        text = text,
        onTextChange = {
            text = it
            viewModel.getAddress(it)
        },
        onClickBack = {
            appState.popBackStack()
        },
        onClickMap = {

        },
        onClickResultSearch = {
            val addressName = it.getPrimaryText(null).toString()
            viewModel.addSearchHistory(addressName)

            val params = mutableMapOf<String, Any>()
            params[Constants.Key.ADDRESS_NAME] = it.getPrimaryText(null).toString()
            appState.popBackStack(params = params)
        },
        onClickHistory = {
            val params = mutableMapOf<String, Any>()
            params[Constants.Key.ADDRESS_NAME] = it.address
            appState.popBackStack(params = params)
        },
        onClearAllHistory = {
            viewModel.clearHistory()
        },
        onDismissErrorDialog = {
            viewModel.hideError()
        },
    )
}