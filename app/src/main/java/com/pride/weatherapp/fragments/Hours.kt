package com.pride.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.pride.weatherapp.databinding.FragmentHoursBinding
import com.pride.weatherapp.logic.RecyclerAdapterHours
import com.pride.weatherapp.logic.WeatherViewModel

class Hours : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    private val weatherVM: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        weatherVM.openedDetail()
        val rcAdapter = RecyclerAdapterHours(requireContext())
        binding.rcHours.adapter = rcAdapter
        binding.rcHours.layoutManager = LinearLayoutManager(requireContext())
        weatherVM.selectedHours.observe(viewLifecycleOwner) {
            if (it!=null) rcAdapter.setData(it)
        }
    }
}