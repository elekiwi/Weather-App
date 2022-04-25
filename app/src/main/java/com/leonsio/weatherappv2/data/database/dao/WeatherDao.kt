package com.leonsio.weatherappv2.data.database.dao

import androidx.room.*
import com.leonsio.weatherappv2.data.database.entities.WeatherEntity

@Dao
interface WeatherDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(weather: WeatherEntity)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(weathers: List<WeatherEntity>)

    @Query("SELECT * FROM weather_table")
    fun getAllWeathers(): List<WeatherEntity>

    @Query("DELETE FROM weather_table")
    suspend fun deleteWeathers()


}