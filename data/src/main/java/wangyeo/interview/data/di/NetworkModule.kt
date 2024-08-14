package wangyeo.interview.data.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import wangyeo.interview.data.remote.api.CurrentWeatherApiService
import wangyeo.interview.data.remote.api.OneCallApiService
import wangyeo.interview.data.remote.call_adapter.FlowCallAdapterFactory
import wangyeo.interview.data.remote.interceptor.HeaderInterceptor
import java.util.concurrent.TimeUnit

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    const val BASE_URL = "https://api.openweathermap.org/data/2.5/"
    @Provides
    fun provideOkHttpClient(
        headerInterceptor: HeaderInterceptor,
    ): OkHttpClient = OkHttpClient.Builder()
        .callTimeout(TIME_OUT, TimeUnit.MINUTES)
        .connectTimeout(TIME_OUT, TimeUnit.MINUTES)
        .addNetworkInterceptor(HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY))
        .addInterceptor(headerInterceptor)
        .build()

    @Provides
    fun provideRetrofit(
        flowCallAdapterFactory: FlowCallAdapterFactory,
        okHttpClient: OkHttpClient,
    ): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(flowCallAdapterFactory)
        .build()

    @Provides
    fun provideOneCallApiService(retrofit: Retrofit): OneCallApiService =
        retrofit.create(OneCallApiService::class.java)

    @Provides
    fun provideCurrentWeatherApiService(retrofit: Retrofit): CurrentWeatherApiService =
        retrofit.create(CurrentWeatherApiService::class.java)

    private const val TIME_OUT = 1L
}
