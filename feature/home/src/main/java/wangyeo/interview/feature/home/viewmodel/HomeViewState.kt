package wangyeo.interview.feature.home.viewmodel

import com.google.android.gms.maps.model.LatLng
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.feature.common.base.ViewState
import wangyeo.interview.feature.home.models.CurrentWeatherViewData
import wangyeo.interview.feature.home.models.HourWeatherViewData

data class HomeViewState(
    override val isLoading: Boolean = false,
    override val error: AppDomainException? = null,
    val isRefresh: Boolean = false,
    val currentWeather: CurrentWeatherViewData? = null,
    val listHourlyWeatherToday: List<HourWeatherViewData> = emptyList(),
    val navigateSearch: LatLng? = null,
    val isRequestPermission: Boolean = false,
) : ViewState(isLoading, error)