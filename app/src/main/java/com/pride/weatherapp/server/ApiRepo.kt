package com.pride.weatherapp.server

import com.pride.weatherapp.clases.Current
import retrofit2.Call

class ApiRepo {
    fun getCurrentWeather(): Call<Current> {
        return ApiInstance.api.getCurrent()
    }
}