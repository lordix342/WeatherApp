package com.pride.weatherapp.clases


import com.google.gson.annotations.SerializedName

data class Forecast(
    @SerializedName("forecastday")
    val forecastday: ArrayList<Forecastday>
)