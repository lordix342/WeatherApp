package com.pride.weatherapp.fragments

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.material.tabs.TabLayoutMediator
import com.pride.weatherapp.clases.WeatherClass
import com.pride.weatherapp.databinding.FragmentMainBinding
import com.pride.weatherapp.logic.PageAdapter
import com.pride.weatherapp.logic.WeatherViewModel

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var plauncher: ActivityResultLauncher<String>
    private val fragmentList = listOf(Days(),Hours())
    private val tableList = listOf("Days","Hours")
    private val weatherVM : WeatherViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        chekPermission()
        initViewPager()
        weatherVM.getWeatherFromRepo()
        weatherVM.currentWeather.observe(viewLifecycleOwner) {
            if (it!=null) initCurrent(it)
        }
        binding.imageSync.setOnClickListener {
            weatherVM.getWeatherFromRepo()
        }
        weatherVM.obsHour.observe(viewLifecycleOwner) {
            if (it) binding.pager.currentItem = 1
        }
    }

    private fun initViewPager() {
        val adapterPager = PageAdapter(activity as FragmentActivity, fragmentList)
        binding.pager.adapter = adapterPager
        TabLayoutMediator(binding.tabLayout,binding.pager){
            tabItem,position -> tabItem.text = tableList[position]
        }.attach()
    }

    private fun initCurrent(dataCurrent : WeatherClass) {
        with(binding) {
            textCountry.text = dataCurrent.location.country
            cityName.text = dataCurrent.location.name
            temparature.text = dataCurrent.current.tempC.toString()+" °C"
            textRegion.text = dataCurrent.location.region
        }
        Glide.with(requireContext())
            .load("http:"+dataCurrent.current.condition.icon)
            .into(binding.imageWeather)
    }

    private fun chekPermission() {
        if (ContextCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != 0
        ) {
            permissionListener()
            plauncher.launch(Manifest.permission.ACCESS_FINE_LOCATION)
        }
    }

    private fun permissionListener() {
        plauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()) {
            if (!it)  chekPermission()
        }
    }
}