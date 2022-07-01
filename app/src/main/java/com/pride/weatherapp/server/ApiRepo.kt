package com.pride.weatherapp.server

import com.pride.weatherapp.clases.WeatherClass
import retrofit2.Call

class ApiRepo {
    fun getCurrentWeather(location:String,language: String): Call<WeatherClass> {
        return ApiInstance.api.getWeather(location,language)
    }
}