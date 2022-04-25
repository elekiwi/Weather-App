package com.leonsio.weatherappv2.data.remote.models

import com.google.gson.annotations.SerializedName
import com.leonsio.weatherappv2.domain.models.Weather


data class WeatherModel(
    @SerializedName("date") val date: String,
    @SerializedName("city") val city: CityModel,
    @SerializedName("temp") val temp: Double,
    @SerializedName("tempType") val tempType: String
)

