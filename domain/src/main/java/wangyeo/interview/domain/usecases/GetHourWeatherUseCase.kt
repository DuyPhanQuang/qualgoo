package wangyeo.interview.domain.usecases

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import wangyeo.interview.core.di.MainDispatcher
import wangyeo.interview.core.extensions.asFlow
import wangyeo.interview.data.models.Hourly
import wangyeo.interview.domain.exceptions.AppDomainException
import wangyeo.interview.domain.repositories.WeatherRepository
import wangyeo.interview.domain.usecases.base.FlowUseCase
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class GetHourWeatherUseCase @Inject constructor(
    @ApplicationContext private val context: Context,
    @MainDispatcher private val mainDispatcher: CoroutineDispatcher,
    private val weatherRepository: WeatherRepository,
) : FlowUseCase<GetHourWeatherUseCase.Params, GetHourWeatherUseCase.Response>(mainDispatcher) {
    override fun execute(params: Params?): Flow<Response> = if (params == null) {
        AppDomainException.SnackBarException(message = "lat lng invalid")
            .asFlow()
    } else {
        weatherRepository.getHourWeather(params.latLng).map { response ->
            val firstTime = response.hourly?.firstOrNull()?.dt ?: 0L

            val today = response.hourly?.filter {
                it.dt != null && it.dt!! <= firstTime + oneDaySeconds()
            } ?: emptyList()

            val tomorrow = response.hourly?.filter {
                it.dt != null && it.dt!! > firstTime + oneDaySeconds() && it.dt!! <= firstTime + twoDaySeconds()
            } ?: emptyList()

            Response(today, tomorrow)
        }
    }

    private fun oneDaySeconds(): Long = TimeUnit.DAYS.toSeconds(1)

    private fun twoDaySeconds(): Long = oneDaySeconds() * 2

    data class Params(
        val latLng: LatLng,
    )

    data class Response(
        val today: List<Hourly>,
        val tomorrow: List<Hourly>,
    )
}
