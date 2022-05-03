package com.leonsio.weatherappv2.data.remote

import android.util.Log
import com.leonsio.weatherappv2.data.remote.models.WeathersResponse
import com.leonsio.weatherappv2.util.InternetConnection
import com.leonsio.weatherappv2.util.Resource
import retrofit2.HttpException
import retrofit2.Response
import javax.inject.Inject

class WeatherService @Inject constructor(
    val api: WeatherApi
) {

    suspend operator fun invoke(): Resource<WeathersResponse> =
        handleApi { api.fetchWeathers() }


    suspend fun <T : Any> handleApi(
        execute: suspend () -> Response<T>
    ): Resource<T> {
        return try {
            val response = execute()
            val body = response.body()
            if (response.isSuccessful && body != null) {
                Resource.Success(body)
            } else {
                Resource.Error(response.message())
            }
        } catch (e: HttpException) {
            Resource.Error(e.message())
        } catch (e: Throwable) {
            Resource.Error("Some error has occurred ${e.message}")
        }
    }


}