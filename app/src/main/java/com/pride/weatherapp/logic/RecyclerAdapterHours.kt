package com.pride.weatherapp.logic

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pride.weatherapp.R
import com.pride.weatherapp.clases.Hour
import com.pride.weatherapp.databinding.ElementRcBinding
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

class RecyclerAdapterHours(private val context: Context) :
    RecyclerView.Adapter<RecyclerAdapterHours.HourHolder>() {

    private var weatherDay = ArrayList<Hour>()

    class HourHolder(item: View) : RecyclerView.ViewHolder(item) {
        private var isExpand = false
        private val binding = ElementRcBinding.bind(itemView)
        fun bind(hour: Hour,  context: Context) = with(binding) {

            val dateFormat =  SimpleDateFormat("yyyy-MM-dd HH:mm")
            val dateLastB = dateFormat.parse(hour.time)
            val date = Date(dateLastB.time ?: 0L)
            textDate.text = date.hours.toString() +":00"
            textWeather.text = hour.condition.text
            Glide.with(context)
                .load("http:" + hour.condition.icon)
                .into(binding.imgWeather)
            binding.cardDay.setOnClickListener {
                if (!isExpand) {
                    with(binding) {
                        textTemp.text = hour.tempC.toString() + " °C"
                        textWind.text = "Wind speed "+hour.windKph.toString() + "Km/h"
                        textChanceOfRain.text = "Chance of rain "+hour.chanceOfRain.toString() + "%"
                        textChanceOfSnow.text = "Chance of snow "+hour.chanceOfSnow.toString() + "%"
                        textTemp.visibility = View.VISIBLE
                        textWind.visibility = View.VISIBLE
                        textChanceOfRain.visibility = View.VISIBLE
                        textChanceOfSnow.visibility = View.VISIBLE
                    }
                } else {
                    with(binding) {
                        textTemp.visibility = View.GONE
                        textWind.visibility = View.GONE
                        textChanceOfRain.visibility = View.GONE
                        textChanceOfSnow.visibility = View.GONE
                    }
                }
                isExpand = !isExpand
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HourHolder {
        return HourHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.element_rc, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return weatherDay.size
    }

    override fun onBindViewHolder(holder: HourHolder, position: Int) {
        holder.bind(weatherDay[position], context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newHours: ArrayList<Hour>) {
        weatherDay.clear()
        weatherDay.addAll(newHours)
        notifyDataSetChanged()
    }
}