package com.pride.weatherapp.server

import com.pride.weatherapp.clases.WeatherClass
import io.reactivex.rxjava3.core.Observable
import retrofit2.Call
import retrofit2.Response

class ApiRepo {
    suspend fun getCurrentWeather(location:String,language: String): Response<WeatherClass> {
        return ApiInstance.api.getWeather(location,language)
    }
}