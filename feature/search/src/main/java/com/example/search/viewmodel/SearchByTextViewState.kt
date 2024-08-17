package com.example.search.viewmodel

import com.example.search.models.HistorySearchAddressViewData
import com.google.android.gms.maps.model.LatLng
import com.google.android.libraries.places.api.model.AutocompletePrediction
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.feature.common.base.ViewState

data class SearchByTextViewState(
    override val isLoading: Boolean = false,
    override val error: AppDomainException? = null,
    val currentLatLng: LatLng? = null,
    val listSearch: List<HistorySearchAddressViewData> = emptyList(),
    val addressPlaceHolder: List<String> = emptyList(),
    val listResult: List<AutocompletePrediction> = emptyList(),
    val navigateToSearchByMap: NavigateToSearchByMapEvent? = null,
) : ViewState(isLoading, error)

data class NavigateToSearchByMapEvent(
    val latLng: LatLng,
    val fromRoute: String,
)