package com.leonsio.weatherappv2.di

import android.content.Context
import androidx.room.Room
import com.leonsio.weatherappv2.data.database.WeatherDatabase
import com.leonsio.weatherappv2.util.Constants.WEATHER_DATABASE_NAME
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RoomModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context) =
        Room.databaseBuilder(
            context,
            WeatherDatabase::class.java,
            WEATHER_DATABASE_NAME
        ).build()

    @Singleton
    @Provides
    fun provideWeatherDao(db: WeatherDatabase) = db.getWeatherDao()


}