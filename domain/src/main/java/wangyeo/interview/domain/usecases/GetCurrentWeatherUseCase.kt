package wangyeo.interview.domain.usecases

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.core.di.MainDispatcher
import wangyeo.interview.core.extensions.asFlow
import wangyeo.interview.data.models.CurrentWeather
import wangyeo.interview.domain.exceptions.AppDomainException
import wangyeo.interview.domain.repositories.WeatherRepository
import wangyeo.interview.domain.usecases.base.FlowUseCase
import javax.inject.Inject

class GetCurrentWeatherUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @MainDispatcher private val coroutineDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository,
) : FlowUseCase<GetCurrentWeatherUseCase.Params, CurrentWeather>(coroutineDispatcher) {
    override fun execute(params: Params?): Flow<CurrentWeather> = if (params != null) {
        weatherRepository.getCurrentWeatherByLocation(params.latLng)
    } else {
        AppDomainException.SnackBarException(message = "lat lng invalid")
            .asFlow()
    }

    data class Params(
        val latLng: LatLng,
    )
}