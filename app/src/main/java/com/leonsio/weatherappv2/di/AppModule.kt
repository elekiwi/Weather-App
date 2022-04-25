package com.leonsio.weatherappv2.di

import android.content.Context
import com.leonsio.weatherappv2.WeatherApplication
import com.leonsio.weatherappv2.data.database.dao.WeatherDao
import com.leonsio.weatherappv2.data.remote.WeatherApi
import com.leonsio.weatherappv2.data.remote.WeatherService
import com.leonsio.weatherappv2.data.repositories.WeatherRepository
import com.leonsio.weatherappv2.data.repositories.WeatherRepositoryImpl
import com.leonsio.weatherappv2.util.Constants
import com.leonsio.weatherappv2.util.DispatcherProvider
import com.leonsio.weatherappv2.util.InternetConnection
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRetrofit() : Retrofit {
        return Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Singleton
    @Provides
    fun provideWeatherAPI(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    @Singleton
    @Provides
    fun provideWeatherRepositoryImpl(
        api: WeatherService,
        dao: WeatherDao
    ) = WeatherRepositoryImpl(dao, api)

    @Singleton
    @Provides
    fun provideDispatchers() : DispatcherProvider = object : DispatcherProvider {
        override val main: CoroutineDispatcher
            get() = Dispatchers.Main
        override val io: CoroutineDispatcher
            get() = Dispatchers.IO
        override val default: CoroutineDispatcher
            get() = Dispatchers.Default
        override val unconfined: CoroutineDispatcher
            get() = Dispatchers.Unconfined

    }
    @Singleton
    @Provides
    fun provideApplicationContext() = WeatherApplication()
}