package com.leonsio.weatherappv2.data.repositories

import com.leonsio.weatherappv2.data.database.entities.WeatherEntity
import com.leonsio.weatherappv2.data.remote.models.WeathersResponse
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.util.Resource

interface WeatherRepository {

    suspend fun insertAll(weathersEntity: List<WeatherEntity>)
    suspend fun deleteWeathers()
    suspend fun fetchWeathersFromApi() : Resource<WeathersResponse>
    suspend fun fetchWeathersFromDatabase() : List<Weather>

}