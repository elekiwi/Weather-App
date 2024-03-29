package com.leonsio.weatherappv2.ui.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.leonsio.weatherappv2.domain.GetWeathersUseCase
import com.leonsio.weatherappv2.domain.NoInternetUseCase
import com.leonsio.weatherappv2.domain.models.Weather
import com.leonsio.weatherappv2.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val fetchWeathersUseCase: GetWeathersUseCase,
    private val noInternetUseCase: NoInternetUseCase,
) : ViewModel() {

    private val _weatherLiveData = MutableLiveData<Resource<List<Weather>>>()
    val weatherLiveData: LiveData<Resource<List<Weather>>> = _weatherLiveData

    var connectivity: Boolean = true

    var uiStateError: Boolean = false

    fun updateUI() {

        viewModelScope.launch {

            if (connectivity) {
                _weatherLiveData.postValue(Resource.Loading())

                val result = fetchWeathersUseCase()

                val list = result.data
                when (result) {
                    is Resource.Success -> {
                        uiStateError = false

                        if (!list.isNullOrEmpty()) {
                            _weatherLiveData.postValue(Resource.Success(list))
                        }
                    }
                    is Resource.Error  -> {
                        _weatherLiveData.postValue(Resource.Error(result.message!!, list))

                    }
                    is Resource.Loading -> Unit
                }
            } else {
                val result = noInternetUseCase()
                val list = result.data

                _weatherLiveData.postValue(Resource.Error(result.message!!, list))


            }

        }
    }


}