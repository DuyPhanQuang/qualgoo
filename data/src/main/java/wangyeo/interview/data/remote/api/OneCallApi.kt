package wangyeo.interview.data.remote.api

import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Query
import wangyeo.interview.data.models.OneCallResponse

interface OneCallApiService {
    @GET("onecall")
    fun getWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "current,daily,alerts,minutes",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appId: String
    ): Flow<OneCallResponse>

    @GET("onecall")
    fun getSevenDaysWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("exclude") exclude: String = "current,alerts,minutes,hourly",
        @Query("units") units: String = "metric",
        @Query("lang") lang: String = "en",
        @Query("appid") appId: String
    ): Flow<OneCallResponse>
}