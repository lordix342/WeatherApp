package com.pride.weatherapp.logic

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pride.weatherapp.clases.Forecast
import com.pride.weatherapp.clases.Hour
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
    private var _selectedHours: MutableLiveData<ArrayList<Hour>> = MutableLiveData()
    var currentWeather: MutableLiveData<WeatherClass> = MutableLiveData()
    var daysWeather: MutableLiveData<Forecast> = MutableLiveData()
    var obsHour : MutableLiveData<Boolean> = MutableLiveData()
    var selectedHours : MutableLiveData<ArrayList<Hour>> = MutableLiveData()


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
                    _selectedHours.value = _daysWeather.value?.forecastday?.get(0)?.hour
                    selectedHours.value = _selectedHours.value
                }

                override fun onFailure(call: Call<WeatherClass>, t: Throwable) {
                    t.printStackTrace()
                }
            })
        }
    }
    fun openDetailInfo(hours : ArrayList<Hour>) {
        viewModelScope.launch {
            obsHour.value = true
            _selectedHours.value = hours
            selectedHours.value = _selectedHours.value
        }
    }
    fun openedDetail() {
        viewModelScope.launch {
            obsHour.value = false
        }
    }
}