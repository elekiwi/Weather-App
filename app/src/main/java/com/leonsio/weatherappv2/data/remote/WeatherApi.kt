package com.leonsio.weatherappv2.data.remote

import com.leonsio.weatherappv2.data.remote.models.WeathersResponse
import com.leonsio.weatherappv2.util.Constants.WEATHER_API_ENDPOINT
import retrofit2.Response
import retrofit2.http.GET


interface WeatherApi {

    @GET(WEATHER_API_ENDPOINT)
    suspend fun fetchWeathers() : Response<WeathersResponse>
}