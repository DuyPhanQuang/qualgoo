package com.example.search.ui.components.text

import ResultSearchEmptyList
import ResultSearchList
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.example.search.models.HistorySearchAddressViewData
import com.example.search.viewmodel.SearchByTextViewState
import com.google.android.libraries.places.api.model.AutocompletePrediction
import wangyeo.interview.feature.common.components.AppScaffold
import wangyeo.interview.feature.common.R

@Composable
fun SearchByTextView(
    state: SearchByTextViewState,
    text: String = "",
    onTextChange: (String) -> Unit = {},
    onClickBack: () -> Unit = {},
    onClickMap: () -> Unit = {},
    onClickResultSearch: (AutocompletePrediction) -> Unit = {},
    onClearAllHistory: () -> Unit = {},
    onClickHistory: (HistorySearchAddressViewData) -> Unit = {},
    onDismissErrorDialog: () -> Unit = {},
) {
    AppScaffold(
        modifier = Modifier
            .fillMaxSize()
            .imePadding(),
        state = state,
        onDismissErrorDialog = onDismissErrorDialog,
        topBar = {
            SearchByTextAppBar(
                text = text,
                placeholder = state.addressPlaceHolder,
                onTextChange = onTextChange,
                onClickBack = onClickBack,
                onClickMap = onClickMap,
            )
        },
    ) { _, _ ->
        LazyColumn(modifier = Modifier.fillMaxSize()) {
            item {
                ResultSearchHeaderSection(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                        .fillMaxWidth(),
                    textTitle = stringResource(id = R.string.history),
                    textAction = stringResource(id = R.string.clear_all),
                    onClickAction = onClearAllHistory,
                )
            }

            if (state.listSearch.isEmpty()) {
                item {
                    ResultSearchEmptyList(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.no_history),
                    )
                }
            } else {
                item {
                    ResultSearchList(
                        modifier = Modifier.fillMaxWidth(),
                        onClickHistoryItem = onClickHistory,
                        listHistory = state.listSearch,
                    )
                }
            }

            item {
                ResultSearchHeaderSection(
                    modifier = Modifier
                        .padding(start = 10.dp, end = 10.dp, top = 20.dp)
                        .fillMaxWidth(),
                    textTitle = stringResource(id = R.string.result),
                )
            }

            if (state.listResult.isEmpty()) {
                item {
                    ResultSearchEmptyList(
                        modifier = Modifier.fillMaxWidth(),
                        text = stringResource(id = R.string.no_result),
                    )
                }
            } else {
                items(items = state.listResult) { item ->
                    ResultSearchItem(
                        modifier = Modifier.fillMaxWidth(),
                        item = item,
                        onClickResultSearch = onClickResultSearch,
                    )
                }
            }
        }
    }
}