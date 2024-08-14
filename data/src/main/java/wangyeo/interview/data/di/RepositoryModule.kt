package wangyeo.interview.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import wangyeo.interview.data.remote.api.CurrentWeatherApiService
import wangyeo.interview.data.remote.api.OneCallApiService
import wangyeo.interview.data.repositories.WeatherRepositoryImpl
import wangyeo.interview.domain.repositories.WeatherRepository

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWeatherRepository(
        currentWeatherApiService: CurrentWeatherApiService,
        oneCallApiService: OneCallApiService,
    ): WeatherRepository = WeatherRepositoryImpl(currentWeatherApiService, oneCallApiService)
}
