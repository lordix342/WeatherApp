package com.pride.weatherapp.clases


import com.google.gson.annotations.SerializedName

data class HourDayClass(
    @SerializedName("current")
    val current: CurrentX,
    @SerializedName("forecast")
    val forecast: Forecast,
    @SerializedName("location")
    val location: Location
)