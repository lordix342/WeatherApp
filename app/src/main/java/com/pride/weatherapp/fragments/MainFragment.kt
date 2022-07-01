package com.pride.weatherapp.fragments

import android.Manifest
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Context.LOCATION_SERVICE
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.location.LocationManager.GPS_PROVIDER
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
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
import java.util.*

class MainFragment : Fragment() {
    private lateinit var binding: FragmentMainBinding
    private lateinit var plauncher: ActivityResultLauncher<String>
    private lateinit var fusedLocationProviderClient: FusedLocationProviderClient
    private lateinit var latitude: String
    private lateinit var longitude: String
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
        chekPermission()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getLocation()
        chekPermission()
        initViewPager()
        weatherVM.currentWeather.observe(viewLifecycleOwner) {
            if (it != null) initCurrent(it)
        }
        binding.imageSync.setOnClickListener {
            getLocation()
        }
        weatherVM.obsHour.observe(viewLifecycleOwner) {
            if (it) binding.pager.currentItem = 1
        }
    }

    private fun getLocation() {
        val locationManager =
            requireActivity().getSystemService(LOCATION_SERVICE) as LocationManager
        if (locationManager.isProviderEnabled(GPS_PROVIDER)) {
            fusedLocationProviderClient =
                LocationServices.getFusedLocationProviderClient(requireContext())
            fusedLocationProviderClient.lastLocation.addOnCompleteListener {
                val location: Location? = it.result
                if (location != null) {
                    latitude = location.latitude.toString()
                    longitude = location.longitude.toString()
                    if (isNetworkConnected(requireContext())) {
                        weatherVM.getWeatherFromRepo(
                            "$latitude,$longitude",
                            Locale.getDefault().language.toString()
                        )
                    } else {
                        Toast.makeText(requireContext(), resources.getString(R.string.error_network), Toast.LENGTH_LONG).show()
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), resources.getString(R.string.error_gps), Toast.LENGTH_LONG).show()
            startActivity(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS))
        }

    }

    private fun isNetworkConnected(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_CELLULAR")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_WIFI")
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    Log.i("Internet", "NetworkCapabilities.TRANSPORT_ETHERNET")
                    return true
                }
            }
        }
        return false
    }

    private fun initViewPager() {
        val tableList = listOf(resources.getString(R.string.days_tab), resources.getString(R.string.hours_tab))
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
            temperature.text = dataCurrent.current.tempC.toString() + " °C"
            textRegion.text = dataCurrent.location.region
        }
        Glide.with(requireContext())
            .load("http:" + dataCurrent.current.condition.icon)
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
            if (!it) chekPermission()
        }
    }
}