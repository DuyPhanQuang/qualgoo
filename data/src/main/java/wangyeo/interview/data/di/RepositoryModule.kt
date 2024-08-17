package wangyeo.interview.data.di

import android.content.Context
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.net.PlacesClient
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wangyeo.interview.data.local.room.HistorySearchAddressDao
import wangyeo.interview.data.remote.api.CurrentWeatherApiService
import wangyeo.interview.data.remote.api.OneCallApiService
import wangyeo.interview.data.repositories.impl.WeatherRepositoryImpl
import wangyeo.interview.data.repositories.LocationRepository
import wangyeo.interview.data.repositories.UserRepository
import wangyeo.interview.data.repositories.WeatherRepository
import wangyeo.interview.data.repositories.impl.LocationRepositoryImpl
import wangyeo.interview.data.repositories.impl.UserRepositoryImpl

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

    @Provides
    fun provideUserRepository(
        searchAddressDao: HistorySearchAddressDao,
    ): UserRepository = UserRepositoryImpl(searchAddressDao)
}
