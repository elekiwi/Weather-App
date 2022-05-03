package com.leonsio.weatherappv2.domain.models

import com.leonsio.weatherappv2.data.database.entities.WeatherEntity
import com.leonsio.weatherappv2.data.remote.models.WeatherModel
import java.text.SimpleDateFormat
import java.util.*

data class Weather(
    val date: String,
    val cityName: String,
    val cityPic: String,
    val temp: Double,
    val tempType: String
)

fun Weather.convertTempToCelsius(): Double {
    when (tempType) {
        "K" -> {
            return temp - 273.15
        }
        "F" -> {
            return (temp - 32) / 1.8
        }
        else -> {
            return temp
        }
    }
}

fun Weather.formatDate(): String {

    try {

        val inputFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.getDefault())
        val outputFormatter = SimpleDateFormat("EEEE, d MMM - HH:mm", Locale.getDefault())
        val mDate = inputFormatter.parse(date) // this never ends while debugging

        mDate.let {
            return outputFormatter.format(mDate!!) ?: date
        }


    } catch (e: Exception) {
        return date
    }

}

fun WeatherModel.toViewModel() = Weather(date, city.name, city.picture, temp, tempType)
fun WeatherEntity.toViewModel() = Weather(date, cityName, cityPic, temp, tempType)