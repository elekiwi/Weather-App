package com.leonsio.weatherappv2.domain

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import com.leonsio.weatherappv2.WeatherApplication
import com.leonsio.weatherappv2.data.database.entities.toDatabase
import com.leonsio.weatherappv2.data.repositories.WeatherRepositoryImpl
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.domain.models.toViewModel
import com.leonsio.weatherappv2.util.Resource
import com.leonsio.weatherappv2.util.Status
import dagger.Provides
import dagger.hilt.android.internal.Contexts.getApplication
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Singleton

class GetWeathersUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl,
) {

    suspend operator fun invoke(): Resource<List<Weather>> {

        val weathers = repository.fetchWeathersFromApi()

        when (weathers.status) {
            Status.SUCCESS -> {

                weathers.data.let { weatherList ->
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.deleteWeathers()
                        repository.insertAll(weatherList!!.map { it.toDatabase() })
                    }

                    return Resource.success(weatherList!!.map { it.toViewModel() })
                }
            }

            Status.ERROR -> {
                var list: List<Weather> = emptyList()
                CoroutineScope(Dispatchers.IO).launch {
                    list = repository.fetchWeathersFromDatabase()

                }.join()

                return Resource.error(weathers.message!!, list)
            }
            else -> {

                var list: List<Weather> = emptyList()
                CoroutineScope(Dispatchers.IO).launch {
                    list = repository.fetchWeathersFromDatabase()
                }.join()

                return Resource.error("Some error has ocurred", list)
            }
        }

    }
}