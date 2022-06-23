package com.pride.weatherapp.clases


import com.google.gson.annotations.SerializedName

data class Current(
    @SerializedName("current")
    val current: CurrentX,
    @SerializedName("location")
    val location: Location
)