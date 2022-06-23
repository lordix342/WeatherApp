package com.pride.weatherapp.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pride.weatherapp.clases.Current
import com.pride.weatherapp.server.ApiRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel:ViewModel() {
    private var repository = ApiRepo()
    private var _currentWeather: MutableLiveData<Current> = MutableLiveData()
    var currentWeather: MutableLiveData<Current> = MutableLiveData()

    fun getCurrentFromRepo() {
        viewModelScope.launch {
            repository.getCurrentWeather().enqueue(object : Callback<Current> {
                override fun onResponse(
                    call: Call<Current>,
                    response: Response<Current>
                ) {
                    _currentWeather.value = response.body()
                    currentWeather.value = _currentWeather.value
                }

                override fun onFailure(call: Call<Current>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }

}