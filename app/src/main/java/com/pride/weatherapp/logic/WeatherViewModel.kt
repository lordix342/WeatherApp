package com.pride.weatherapp.logic

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.pride.weatherapp.clases.Forecast
import com.pride.weatherapp.clases.Hour
import com.pride.weatherapp.clases.WeatherClass
import com.pride.weatherapp.room.DataBase
import com.pride.weatherapp.room.Name
import com.pride.weatherapp.server.ApiRepo
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.schedulers.Schedulers.newThread
import kotlinx.coroutines.launch

class WeatherViewModel(application:Application):AndroidViewModel(application) {
    private val dataBase = DataBase.getDatabase(application.applicationContext)
    private val repository = ApiRepo()
    private var _currentWeather = MutableLiveData<WeatherClass>()
    private var _daysWeather = MutableLiveData<Forecast>()
    private var _selectedHours = MutableLiveData<ArrayList<Hour>>()
    val checkPermission : LiveData<Name>
        get() {
            val permission = MutableLiveData<Name>()
            dataBase.permissionDao().findText("Permission")
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    permission.value = it
                },{
                })
            return permission
        }
    val currentWeather: LiveData<WeatherClass>
        get() = _currentWeather
    val daysWeather: LiveData<Forecast>
        get() = _daysWeather
    val selectedHours : LiveData<ArrayList<Hour>>
        get() = _selectedHours
    var obsHour = MutableLiveData<Boolean>()
    var message = MutableLiveData<String>()

    fun givePermission() {
        viewModelScope.launch {
            dataBase.permissionDao().insertToDB(Name(null,"Permission", true))
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                    Log.d("error","error $it")
                })
        }
    }

    fun getWeatherFromRepo(location:String,language: String) {
        viewModelScope.launch {
            repository.getCurrentWeather(location,language)
                .subscribeOn(newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    _currentWeather.value = it
                    _daysWeather.value = _currentWeather.value?.forecast
                    _selectedHours.value = _daysWeather.value?.forecastday?.get(0)?.hour
                }, {
                    message.value = it.message.toString()
                })
        }
    }

    fun openDetailInfo(hours : ArrayList<Hour>) {
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