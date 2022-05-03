package com.leonsio.weatherappv2.domain

import com.leonsio.weatherappv2.data.database.entities.toDatabase
import com.leonsio.weatherappv2.data.repositories.WeatherRepositoryImpl
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.domain.models.toViewModel
import com.leonsio.weatherappv2.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class GetWeathersUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl,
) {

    suspend operator fun invoke(): Resource<List<Weather>> {

        val weathers = repository.fetchWeathersFromApi()


        when (weathers) {
            is Resource.Success -> {

                weathers.data.let { weatherList ->
                    CoroutineScope(Dispatchers.IO).launch {
                        repository.deleteWeathers()
                        repository.insertAll(weatherList!!.map { it.toDatabase() })
                    }

                    return Resource.Success(weatherList!!.map { it.toViewModel() })
                }
            }

            is Resource.Error -> {
                var list: List<Weather> = emptyList()
                CoroutineScope(Dispatchers.IO).launch {
                    list = repository.fetchWeathersFromDatabase()

                }.join()

                return Resource.Error(weathers.message!!, list)
            }
            is Resource.Loading -> return Resource.Loading()

        }

    }
}