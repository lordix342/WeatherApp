package com.pride.weatherapp.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pride.weatherapp.clases.Day
import com.pride.weatherapp.clases.Forecast
import com.pride.weatherapp.clases.WeatherClass
import com.pride.weatherapp.server.ApiRepo
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class WeatherViewModel:ViewModel() {
    private var repository = ApiRepo()
    private var _currentWeather: MutableLiveData<WeatherClass> = MutableLiveData()
    private var _daysWeather: MutableLiveData<Forecast> = MutableLiveData()
    var currentWeather: MutableLiveData<WeatherClass> = MutableLiveData()
    var daysWeather: MutableLiveData<Forecast> = MutableLiveData()
    var obsHour : MutableLiveData<Boolean> = MutableLiveData()

    fun getWeatherFromRepo() {
        viewModelScope.launch {
            repository.getCurrentWeather().enqueue(object : Callback<WeatherClass> {
                override fun onResponse(
                    call: Call<WeatherClass>,
                    response: Response<WeatherClass>
                ) {
                    _currentWeather.value = response.body()
                    currentWeather.value = _currentWeather.value
                    _daysWeather.value = _currentWeather.value?.forecast
                    daysWeather.value = _daysWeather.value
                }

                override fun onFailure(call: Call<WeatherClass>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
    fun openDetailInfo() {
        viewModelScope.launch {
            obsHour.value = true
        }
    }
    fun openedDetail(day: Day) {
        viewModelScope.launch {
            obsHour.value = false
        }
    }

}