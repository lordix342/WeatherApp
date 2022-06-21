package com.pride.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pride.weatherapp.fragments.MainFragment

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportFragmentManager.beginTransaction().replace(R.id.placeholder, MainFragment()).commit()
    }
}