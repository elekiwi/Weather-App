package com.leonsio.weatherappv2.data.remote.models

import com.google.gson.annotations.SerializedName


data class WeatherModel(
    @SerializedName("date") val date: String,
    @SerializedName("city") val city: CityModel,
    @SerializedName("temp") val temp: Double,
    @SerializedName("tempType") val tempType: String
)

