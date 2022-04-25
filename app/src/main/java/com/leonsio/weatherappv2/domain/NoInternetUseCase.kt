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

class NoInternetUseCase @Inject constructor(
    private val repository: WeatherRepositoryImpl,
) {

    suspend operator fun invoke(): Resource<List<Weather>> {

        var list: List<Weather> = emptyList()
        CoroutineScope(Dispatchers.IO).launch {
            list = repository.fetchWeathersFromDatabase()

        }.join()

        return Resource.error("No internet. Check your connection", list)
    }

}