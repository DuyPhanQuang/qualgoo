package wangyeo.interview.domain.repositories.impl

import androidx.compose.ui.text.intl.Locale
import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.data.models.CurrentWeather
import wangyeo.interview.data.models.OneCallResponse
import wangyeo.interview.data.remote.api.CurrentWeatherApiService
import wangyeo.interview.data.remote.api.OneCallApiService
import wangyeo.interview.domain.repositories.WeatherRepository
import javax.inject.Inject

const val API_KEY = ""

class WeatherRepositoryImpl @Inject constructor(
    private val currentWeatherApiService: CurrentWeatherApiService,
    private val oneCallApiService: OneCallApiService,
) : WeatherRepository {
    override fun getCurrentWeatherByCity(city: String): Flow<CurrentWeather> =
        currentWeatherApiService.getCurrentWeatherByCity(
            city = city,
            lang = Locale.current.language,
            appId = API_KEY,
        )

    override fun getCurrentWeatherByLocation(latLng: LatLng): Flow<CurrentWeather> =
        currentWeatherApiService.getCurrentWeatherByLocation(
            latitude = latLng.latitude,
            longitude = latLng.longitude,
            lang = Locale.current.language,
            appId = API_KEY,
        )

    override fun getHourWeather(latLng: LatLng): Flow<OneCallResponse> = oneCallApiService.getWeather(
        lat = latLng.latitude,
        lon = latLng.longitude,
        lang = Locale.current.language,
        appId = API_KEY,
    )

    override fun getSevenDaysWeather(latLng: LatLng): Flow<OneCallResponse> = oneCallApiService.getSevenDaysWeather(
        lat = latLng.latitude,
        lon = latLng.longitude,
        lang = Locale.current.language,
        appId = API_KEY,
    )
}
