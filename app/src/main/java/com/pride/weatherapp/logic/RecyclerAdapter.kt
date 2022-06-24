package com.pride.weatherapp.logic

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.pride.weatherapp.R
import com.pride.weatherapp.clases.Forecastday
import com.pride.weatherapp.databinding.ElementRcBinding

class RecyclerAdapter(private val context: Context, private val clickListener: ClickListener) :
    RecyclerView.Adapter<RecyclerAdapter.DayHolder>() {

    private var weatherDay = ArrayList<Forecastday>()

    class DayHolder(item: View) : RecyclerView.ViewHolder(item) {
        private var isExpand = false
        private val binding = ElementRcBinding.bind(itemView)
        fun bind(day: Forecastday, clickListener: ClickListener, context: Context) = with(binding) {
            textDate.text = day.date
            textWeather.text = day.day.condition.text
            Glide.with(context)
                .load("http:" + day.day.condition.icon)
                .into(binding.imgWeather)
            binding.cardDay.setOnClickListener {
                //clickListener.onClick(itemView)
                if (!isExpand) {
                    with(binding) {
                        textTemp.text = day.day.maxtempC.toString() + "/" + day.day.mintempC.toString() + " °C"
                        textWind.text = "Wind speed "+day.day.maxwindKph.toString() + "Km/h"
                        textChanceOfRain.text = "Chance of rain "+day.day.dailyChanceOfRain.toString() + "%"
                        textChanceOfSnow.text = "Chance of snow "+day.day.dailyChanceOfSnow.toString() + "%"
                        textTemp.visibility = View.VISIBLE
                        textWind.visibility = View.VISIBLE
                        textChanceOfRain.visibility = View.VISIBLE
                        textChanceOfSnow.visibility = View.VISIBLE
                        bMoreInfo.visibility = View.VISIBLE
                    }
                } else {
                    with(binding) {
                        textTemp.visibility = View.GONE
                        textWind.visibility = View.GONE
                        textChanceOfRain.visibility = View.GONE
                        textChanceOfSnow.visibility = View.GONE
                        bMoreInfo.visibility = View.GONE
                    }
                }
                isExpand = !isExpand
            }
            binding.bMoreInfo.setOnClickListener {
                clickListener.onClick(day)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DayHolder {
        return DayHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.element_rc, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return weatherDay.size
    }

    override fun onBindViewHolder(holder: DayHolder, position: Int) {
        holder.bind(weatherDay[position], clickListener, context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newDays: ArrayList<Forecastday>) {
        weatherDay.clear()
        weatherDay.addAll(newDays)
        notifyDataSetChanged()
    }
}
interface ClickListener {
    fun onClick(day: Forecastday)
}