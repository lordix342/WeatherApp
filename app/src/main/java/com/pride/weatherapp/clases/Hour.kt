package com.pride.weatherapp.clases


import com.google.gson.annotations.SerializedName

data class Hour(
    @SerializedName("chance_of_rain")
    val chanceOfRain: Int,
    @SerializedName("chance_of_snow")
    val chanceOfSnow: Int,
    @SerializedName("cloud")
    val cloud: Int,
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("humidity")
    val humidity: Int,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("time")
    val time: String,
    @SerializedName("will_it_rain")
    val willItRain: Int,
    @SerializedName("will_it_snow")
    val willItSnow: Int,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double
)