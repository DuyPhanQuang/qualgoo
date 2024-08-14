package wangyeo.interview.domain.repositories

import com.google.android.gms.maps.model.LatLng
import kotlinx.coroutines.flow.Flow
import wangyeo.interview.data.models.CurrentWeather
import wangyeo.interview.data.models.OneCallResponse

interface WeatherRepository {
    fun getCurrentWeatherByCity(city: String): Flow<CurrentWeather>

    fun getCurrentWeatherByLocation(latLng: LatLng): Flow<CurrentWeather>

    fun getHourWeather(latLng: LatLng): Flow<OneCallResponse>

    fun getSevenDaysWeather(latLng: LatLng): Flow<OneCallResponse>
}
