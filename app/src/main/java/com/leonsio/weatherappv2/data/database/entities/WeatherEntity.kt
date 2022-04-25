package com.leonsio.weatherappv2.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.leonsio.weatherappv2.data.remote.models.WeatherModel
import com.leonsio.weatherappv2.util.Constants.WEATHER_TABLE

@Entity(
    tableName = WEATHER_TABLE
)

data class WeatherEntity(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "date") val date: String,
    @ColumnInfo(name = "cityName") val cityName: String,
    @ColumnInfo(name = "cityPic") val cityPic: String,
    @ColumnInfo(name = "temp") val temp: Double,
    @ColumnInfo(name = "tempType") val tempType: String
)

fun WeatherModel.toDatabase() = WeatherEntity(date = date, cityName = city.name, cityPic = city.picture, temp = temp, tempType = tempType)
