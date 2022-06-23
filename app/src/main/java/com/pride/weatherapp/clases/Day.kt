package com.pride.weatherapp.clases


import com.google.gson.annotations.SerializedName

data class Day(
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("daily_chance_of_rain")
    val dailyChanceOfRain: Int,
    @SerializedName("daily_chance_of_snow")
    val dailyChanceOfSnow: Int,
    @SerializedName("maxtemp_c")
    val maxtempC: Double,
    @SerializedName("maxwind_kph")
    val maxwindKph: Double,
    @SerializedName("mintemp_c")
    val mintempC: Double,
    @SerializedName("totalprecip_mm")
    val totalprecipMm: Double
)