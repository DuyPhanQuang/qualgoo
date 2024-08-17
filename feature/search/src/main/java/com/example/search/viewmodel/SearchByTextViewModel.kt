package com.example.search.viewmodel

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.SavedStateHandle
import com.example.search.models.HistorySearchAddressViewData
import com.example.search.models.HistorySearchAddressViewDataMapper
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flatMapConcat
import kotlinx.coroutines.flow.update
import wangyeo.interview.domain.usecases.AddSearchAddressUseCase
import wangyeo.interview.domain.usecases.ClearAllSearchAddressUseCase
import wangyeo.interview.domain.usecases.GetAddressFromLocationUseCase
import wangyeo.interview.domain.usecases.GetAddressFromTextUseCase
import wangyeo.interview.domain.usecases.GetCurrentLocationUseCase
import wangyeo.interview.domain.usecases.GetSearchAddressUseCase
import wangyeo.interview.feature.common.base.BaseViewModel
import wangyeo.interview.feature.common.R
import javax.inject.Inject

@OptIn(ExperimentalCoroutinesApi::class)
@SuppressLint("StaticFieldLeak")
@HiltViewModel
class SearchByTextViewModel @Inject constructor(
    @ApplicationContext private val context: Context, // No leak in here!
    private val savedStateHandle: SavedStateHandle,
    private val getAddressFromTextUseCase: GetAddressFromTextUseCase,
    private val getCurrentLocationUseCase: GetCurrentLocationUseCase,
    private val getAddressFromLocationUseCase: GetAddressFromLocationUseCase,
    private val addSearchAddressUseCase: AddSearchAddressUseCase,
    private val getSearchAddressUseCase: GetSearchAddressUseCase,
    private val historySearchAddressViewDataMapper: HistorySearchAddressViewDataMapper,
    private val clearAllSearchAddressUseCase: ClearAllSearchAddressUseCase,
) : BaseViewModel() {
    private val placeHolder: MutableList<String> =
        mutableListOf(context.getString(R.string.search_every_where_you_want))

    private val _state = MutableStateFlow(SearchByTextViewState(addressPlaceHolder = placeHolder))
    val state: StateFlow<SearchByTextViewState> = _state

    init {
        retryViewModelScope {
            getSearchAddressUseCase().collect { listSearch ->
                _state.update {
                    it.copy(
                        listSearch = listSearch.take(10).map { search ->
                            historySearchAddressViewDataMapper.mapToViewData(search)
                        },
                    )
                }
            }
        }

        retryViewModelScope {
            getCurrentLocationUseCase().flatMapConcat { latLng ->
                getAddressFromLocationUseCase(GetAddressFromLocationUseCase.Params(latLng))
            }.collect { address ->
                _state.update {
                    val latLng = LatLng(address.latitude, address.longitude)
                    it.copy(
                        currentLatLng = latLng,
                        addressPlaceHolder = placeHolder.apply { add(address.getAddressLine(0)) },
                    )
                }
            }
        }
    }

    override fun hideError() {
        _state.update {
            it.copy(
                isLoading = false,
                error = null,
            )
        }
    }

    fun cleanEvent() {
        _state.update {
            it.copy(
                navigateToSearchByMap = null,
            )
        }
    }

    fun updatePlaceHolder(default: String) {
        val newPlaceHolder = placeHolder.apply {
            set(0, default)
        }
        _state.update {
            it.copy(
                addressPlaceHolder = newPlaceHolder
            )
        }
    }

    fun getAddress(text: String) {
        if (text.isBlank()) {
            _state.update {
                it.copy(listResult = emptyList())
            }
        } else {
            retryViewModelScope {
                getAddressFromTextUseCase(GetAddressFromTextUseCase.Params(text)).collect { listAddress ->
                    _state.update { state ->
                        state.copy(
                            listResult = listAddress,
                        )
                    }
                }
            }
        }
    }

    fun addSearchHistory(address: String) {
        retryViewModelScope {
            val entity =
                historySearchAddressViewDataMapper.mapToModel(HistorySearchAddressViewData(address))

            addSearchAddressUseCase.invoke(AddSearchAddressUseCase.Params(entity))
        }
    }

    fun clearHistory() {
        retryViewModelScope {
            clearAllSearchAddressUseCase.invoke()
        }
    }


}