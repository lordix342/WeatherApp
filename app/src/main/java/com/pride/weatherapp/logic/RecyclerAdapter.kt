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
    RecyclerView.Adapter<RecyclerAdapter.NoteHolder>() {

    private var notes = ArrayList<Forecastday>()

    class NoteHolder(item: View) : RecyclerView.ViewHolder(item) {

        private val binding = ElementRcBinding.bind(itemView)
        fun bind(day: Forecastday, clickListener: ClickListener, context: Context) = with(binding) {
            textDate.text = day.date
            textWeather.text = day.day.condition.text
            Glide.with(context)
                .load("http:" + day.day.condition.icon)
                .into(binding.imgWeather)
            binding.cardDay.setOnClickListener {
                clickListener.onClick(day)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteHolder {
        return NoteHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.element_rc, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return notes.size
    }

    override fun onBindViewHolder(holder: NoteHolder, position: Int) {
        holder.bind(notes[position], clickListener, context)
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setData(noteslist: ArrayList<Forecastday>) {
        notes.clear()
        notes.addAll(noteslist)
        notifyDataSetChanged()
    }
}
interface ClickListener {
    fun onClick(day: Forecastday)
}