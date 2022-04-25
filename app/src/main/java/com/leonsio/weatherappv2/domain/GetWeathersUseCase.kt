package com.leonsio.weatherappv2.domain

import com.leonsio.weatherappv2.data.database.entities.toDatabase
import com.leonsio.weatherappv2.data.repositories.WeatherRepositoryImpl
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.domain.models.toViewModel
import com.leonsio.weatherappv2.util.Resource
import com.leonsio.weatherappv2.util.Status
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetWeathersUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl
) {

    suspend operator fun invoke(): Resource<List<Weather>> {

        //check internet
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