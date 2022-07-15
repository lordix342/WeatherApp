package com.pride.weatherapp.server

import com.pride.weatherapp.clases.WeatherClass
import io.reactivex.rxjava3.core.Observable

class ApiRepo {
    fun getCurrentWeather(location:String,language: String): Observable<WeatherClass> {
        return ApiInstance.api.getWeather(location,language)
    }
}