package com.leonsio.weatherappv2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonsio.weatherappv2.data.database.entities.WeatherEntity
import com.leonsio.weatherappv2.data.repositories.WeatherRepository
import com.leonsio.weatherappv2.domain.GetWeathersUseCase
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.util.DispatcherProvider
import com.leonsio.weatherappv2.util.Resource
import com.leonsio.weatherappv2.util.Status
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.concurrent.fixedRateTimer

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val useCaseFetchWeather: GetWeathersUseCase
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<Resource<List<Weather>>>()
    val weatherLiveData: LiveData<Resource<List<Weather>>> = _weatherLiveData

    private val _networkError = MutableLiveData<Resource<Boolean>>()
    val networkError: LiveData<Resource<Boolean>> = _networkError

    fun updateUI() {

        viewModelScope.launch {
            _weatherLiveData.postValue(Resource.loading(null))
            //check internet connection???? OR USE CAase
            val result = useCaseFetchWeather()
            val list = result.data
            when (result.status) {
                Status.SUCCESS -> {
                    if (!list.isNullOrEmpty()){
                        _weatherLiveData.postValue(Resource.success(list))
                    }
                }
                Status.ERROR -> {
                    _weatherLiveData.postValue(Resource.error(result.message!!, list))
                }
                Status.LOADING -> Unit
            }

        }
    }

}