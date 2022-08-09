package com.pride.weatherapp.server

import com.pride.weatherapp.clases.WeatherClass
import retrofit2.Response
import javax.inject.Inject

class ApiRepo @Inject constructor(private val apiInterface: ApiInterface) {

    suspend fun getCurrentWeather(location:String,language: String): Response<WeatherClass> {
        return apiInterface.getWeather(location,language)
    }
}