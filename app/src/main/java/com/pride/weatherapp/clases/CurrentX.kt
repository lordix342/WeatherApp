package com.pride.weatherapp.clases


import com.google.gson.annotations.SerializedName

data class CurrentX(
    @SerializedName("cloud")
    val cloud: Int,
    @SerializedName("condition")
    val condition: Condition,
    @SerializedName("feelslike_c")
    val feelslikeC: Double,
    @SerializedName("is_day")
    val isDay: Int,
    @SerializedName("last_updated")
    val lastUpdated: String,
    @SerializedName("temp_c")
    val tempC: Double,
    @SerializedName("uv")
    val uv: Double,
    @SerializedName("wind_degree")
    val windDegree: Int,
    @SerializedName("wind_dir")
    val windDir: String,
    @SerializedName("wind_kph")
    val windKph: Double
)

data class Condition(
    @SerializedName("icon")
    val icon: String,
    @SerializedName("text")
    val text: String
)