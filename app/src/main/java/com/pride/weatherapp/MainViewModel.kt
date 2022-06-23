package com.pride.weatherapp

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class MainViewModel : ViewModel() {
    private var _weatherList : MutableLiveData<ArrayList<Int>> = MutableLiveData()
    var weatherList : MutableLiveData<ArrayList<Int>> = MutableLiveData()
}