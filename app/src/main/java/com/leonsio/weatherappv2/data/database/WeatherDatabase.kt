package com.leonsio.weatherappv2.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.leonsio.weatherappv2.data.database.dao.WeatherDao
import com.leonsio.weatherappv2.data.database.entities.WeatherEntity

@Database(
    entities = [WeatherEntity::class],
    version = 1,
    exportSchema = false
)

abstract class WeatherDatabase : RoomDatabase() {

    abstract fun getWeatherDao() : WeatherDao
}