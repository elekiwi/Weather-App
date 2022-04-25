package com.leonsio.weatherappv2.data.remote.models

import com.google.gson.annotations.SerializedName

data class CityModel(
    @SerializedName("name") val name: String,
    @SerializedName("picture") val picture: String,
)