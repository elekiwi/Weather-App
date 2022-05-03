package com.leonsio.weatherappv2.domain

import com.leonsio.weatherappv2.data.repositories.WeatherRepositoryImpl
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.util.Resource
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class NoInternetUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl,
) {

    suspend operator fun invoke(): Resource<List<Weather>> {

        var list: List<Weather> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            list = repository.fetchWeathersFromDatabase()

        }.join()

        return Resource.Error("No internet. Check your connection", list)
    }

}