package wangyeo.interview.data.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import wangyeo.interview.data.local.room.AppDatabase
import wangyeo.interview.data.local.room.HistorySearchAddressDao
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RoomModule {
    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context,
    ): AppDatabase =
        Room.databaseBuilder(context, AppDatabase::class.java, AppDatabase.database_name).build()

    @Provides
    @Singleton
    fun provideSearchWeatherDao(
        weatherDatabase: AppDatabase
    ): HistorySearchAddressDao = weatherDatabase.historySearchAddressDao()
}
