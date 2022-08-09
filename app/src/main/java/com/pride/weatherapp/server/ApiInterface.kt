package com.pride.weatherapp.server

import com.pride.weatherapp.clases.WeatherClass
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
        @GET("v1/forecast.json?key=bdbb753421a44e00a37171142222106&days=5&aqi=no&alerts=no")
        suspend fun getWeather(@Query("q")location: String, @Query("lang") language: String): Response<WeatherClass>
}