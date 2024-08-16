package wangyeo.interview.domain.usecases

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.core.di.IoDispatcher
import wangyeo.interview.core.extensions.asFlow
import wangyeo.interview.data.models.OneCallResponse
import wangyeo.interview.data.exceptions.AppDomainException
import wangyeo.interview.domain.repositories.WeatherRepository
import wangyeo.interview.domain.usecases.base.FlowUseCase
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetSevenDaysWeatherUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository,
) : FlowUseCase<GetSevenDaysWeatherUseCase.Params, OneCallResponse>(ioDispatcher) {
    override fun execute(params: Params?): Flow<OneCallResponse> = if (params == null) {
        AppDomainException.SnackBarException(message = "lat lng invalid")
            .asFlow()
    } else {
        weatherRepository.getSevenDaysWeather(params.latLng)
    }

    data class Params(
        val latLng: LatLng,
    )
}
