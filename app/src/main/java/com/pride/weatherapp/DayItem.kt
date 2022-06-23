package com.pride.weatherapp

data class DayItem(
    val city : String,
    val region : String,
    val time : String,
    val condition : String,
    val imageUrl : String,
    val currentTemp: String,
    val maxTemp: String,
    val minTemp: String,
    val hours: String
)


