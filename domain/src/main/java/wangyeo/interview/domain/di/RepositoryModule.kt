package wangyeo.interview.domain.di

import android.content.Context
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wangyeo.interview.data.remote.api.CurrentWeatherApiService
import wangyeo.interview.data.remote.api.OneCallApiService
import wangyeo.interview.domain.repositories.impl.WeatherRepositoryImpl
import wangyeo.interview.domain.repositories.LocationRepository
import wangyeo.interview.domain.repositories.WeatherRepository
import wangyeo.interview.domain.repositories.impl.LocationRepositoryImpl

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {
    @Provides
    fun provideWeatherRepository(
        currentWeatherApiService: CurrentWeatherApiService,
        oneCallApiService: OneCallApiService,
    ): WeatherRepository = WeatherRepositoryImpl(currentWeatherApiService, oneCallApiService)

    @Provides
    fun provideLocationRepository(
        @ApplicationContext context: Context,
        token: AutocompleteSessionToken,
        placesClient: PlacesClient,
    ): LocationRepository = LocationRepositoryImpl(context, token, placesClient)
}
