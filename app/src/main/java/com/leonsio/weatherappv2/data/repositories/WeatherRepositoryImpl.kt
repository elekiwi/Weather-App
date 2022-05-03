package com.leonsio.weatherappv2.data.repositories

import com.leonsio.weatherappv2.data.database.dao.WeatherDao
import com.leonsio.weatherappv2.data.database.entities.WeatherEntity
import com.leonsio.weatherappv2.data.remote.WeatherService
import com.leonsio.weatherappv2.data.remote.models.WeathersResponse
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.domain.models.toViewModel
import com.leonsio.weatherappv2.util.Resource
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val weatherDao: WeatherDao,
    private val remoteSource: WeatherService
) : WeatherRepository {


    override suspend fun insertAll(weathersEntity: List<WeatherEntity>) {
        weatherDao.insertAll(weathersEntity)
    }

    override suspend fun deleteWeathers() {
        weatherDao.deleteWeathers()
    }


    override suspend fun fetchWeathersFromApi(): Resource<WeathersResponse> {
        return remoteSource.invoke()
    }

    override suspend fun fetchWeathersFromDatabase(): List<Weather> {
        return weatherDao.getAllWeathers().map { it.toViewModel() }
    }


}