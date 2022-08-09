package com.pride.weatherapp.logic

import androidx.lifecycle.*
import com.pride.weatherapp.clases.Forecast
import com.pride.weatherapp.clases.Hour
import com.pride.weatherapp.clases.WeatherClass
import com.pride.weatherapp.room.DataBase
import com.pride.weatherapp.room.Name
import com.pride.weatherapp.server.ApiRepo
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val dataBase: DataBase,
    private val apiWeather: ApiRepo
) : ViewModel() {

    private var _currentWeather = MutableLiveData<WeatherClass>()
    private var _daysWeather = MutableLiveData<Forecast>()
    private var _selectedHours = MutableLiveData<ArrayList<Hour>>()

    val checkPermission = MutableLiveData(Name(null, "Permission", true))
    val currentWeather: LiveData<WeatherClass>
        get() = _currentWeather
    val daysWeather: LiveData<Forecast>
        get() = _daysWeather
    val selectedHours: LiveData<ArrayList<Hour>>
        get() = _selectedHours
    var obsHour = MutableLiveData<Boolean>()


    fun getPermission() {
        viewModelScope.launch {
            checkPermission.value = CoroutineScope(Dispatchers.IO).async {
                return@async dataBase.permissionDao().findText("Permission")
            }.await()
        }
    }

    fun sendPermission() {
        CoroutineScope(Dispatchers.IO).launch {
            dataBase.permissionDao().insertToDB(Name(null, "Permission", true))
        }
    }

    fun getWeatherFromRepo(location: String, language: String) {
        viewModelScope.launch {
            _currentWeather.value = apiWeather.getCurrentWeather(location, language).body()
            _daysWeather.value = _currentWeather.value?.forecast
            _selectedHours.value = _daysWeather.value?.forecastday?.get(0)?.hour
        }
    }

    fun openDetailInfo(hours: ArrayList<Hour>) {
        viewModelScope.launch {
            obsHour.value = true
            _selectedHours.value = hours
        }
    }

    fun openedDetail() {
        viewModelScope.launch {
            obsHour.value = false
        }
    }
}