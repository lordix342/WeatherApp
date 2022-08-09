package com.pride.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pride.weatherapp.clases.Forecastday
import com.pride.weatherapp.databinding.FragmentDaysBinding
import com.pride.weatherapp.logic.ClickListener
import com.pride.weatherapp.logic.RecyclerAdapter
import com.pride.weatherapp.logic.WeatherViewModel


class Days : Fragment(), ClickListener {
    private lateinit var binding:FragmentDaysBinding
    private val weatherVM : WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDaysBinding.inflate(inflater)
        return binding.root
    }

    override fun onClick(day: Forecastday) {
      weatherVM.openDetailInfo(day.hour)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val rcAdapter = RecyclerAdapter(requireContext(), this)
        binding.rcDays.adapter = rcAdapter
        binding.rcDays.layoutManager = LinearLayoutManager(requireContext())
        weatherVM.daysWeather.observe(viewLifecycleOwner) {
            if (it!=null) {
                rcAdapter.setData(it.forecastday)
            }
        }
    }
}