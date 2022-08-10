package com.pride.weatherapp.fragments


import android.Manifest
import android.content.Context
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.activityViewModels
import com.bumptech.glide.Glide
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.material.tabs.TabLayoutMediator
import com.pride.weatherapp.R
import com.pride.weatherapp.clases.WeatherClass
import com.pride.weatherapp.databinding.FragmentMainBinding
import com.pride.weatherapp.logic.PageAdapter
import com.pride.weatherapp.logic.WeatherViewModel
import com.pride.weatherapp.server.NetworkHelper
import java.util.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private val fragmentList = listOf(Days(), Hours())
    private val weatherVM: WeatherViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(inflater)
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        getLocation()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initViewPager()
        weatherVM.obsHour.observe(viewLifecycleOwner) {
            if (it) binding.pager.currentItem = 1
        }
        chekGPSAgree()
        binding.bTryGps.setOnClickListener {
            getAgree()
        }
        getLocation()
        weatherVM.currentWeather.observe(viewLifecycleOwner) {
            if (it != null) initCurrent(it)
        }
        binding.imageSync.setOnClickListener {
            getLocation()
        }
        binding.bTry.setOnClickListener {
            binding.internetError.visibility = View.GONE
            getLocation()
        }
    }

    private fun getLocation() {
        weatherVM.getPermission()
        weatherVM.checkPermission.observe(viewLifecycleOwner) { confirmPermission ->
            if (confirmPermission?.confirmation == true) {
                val locationManager =
                    requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
                if (locationManager.isProviderEnabled(GPS_PROVIDER)) {
                    binding.gpsEnable.visibility = View.GONE
                    fusedLocationProviderClient =
                        LocationServices.getFusedLocationProviderClient(requireContext())
                    checkingPermission()
                    fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                        val location: Location? = it.result
                        if (location != null) {
                            isConnected(
                                location.latitude.toString(),
                                location.longitude.toString()
                            )
                        }
                    }
                } else {
                    if ((!binding.gpsError.isVisible) && (!binding.internetError.isVisible)) {
                        binding.gpsEnable.visibility = View.VISIBLE
                        binding.bEnableGps.setOnClickListener {
                            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
                        }
                    }
                }
            } else {
                Toast.makeText(requireContext(), "No permission", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun isConnected(latitude: String, longitude: String) {
        if (NetworkHelper(requireContext()).isNetworkConnected()) {
            weatherVM.getWeatherFromRepo(
                "$latitude,$longitude",
                Locale.getDefault().language.toString()
            )
        } else {
            Toast.makeText(
                requireContext(),
                "No internet connection",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun checkingPermission() {
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            Toast.makeText(
                requireContext(),
                "No permission to GPS",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun chekGPSAgree() {
        val sharedPref = activity?.getPreferences(Context.MODE_PRIVATE)?.getInt("Agree", 0)
        if (sharedPref != 1) binding.gpsError.visibility = View.VISIBLE
    }

    private fun getAgree() {
        activity?.getPreferences(Context.MODE_PRIVATE)?.edit {
            putInt("Agree", 1)
            apply()
        }
        binding.gpsError.visibility = View.GONE
        getLocation()
    }

    private fun initViewPager() {
        val tableList =
            listOf(resources.getString(R.string.days_tab), resources.getString(R.string.hours_tab))
        val adapterPager = PageAdapter(activity as FragmentActivity, fragmentList)
        binding.pager.adapter = adapterPager
        TabLayoutMediator(binding.tabLayout, binding.pager) { tabItem, position ->
            tabItem.text = tableList[position]
        }.attach()
    }

    private fun initCurrent(dataCurrent: WeatherClass) {
        with(binding) {
            textCountry.text = dataCurrent.location.country
            cityName.text = dataCurrent.location.name
            temperature.text = "${dataCurrent.current.tempC} °C"
            textRegion.text = dataCurrent.location.region
        }
        Glide.with(requireContext())
            .load("http:" + dataCurrent.current.condition.icon)
            .into(binding.imageWeather)
    }
}