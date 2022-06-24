package com.pride.weatherapp.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.pride.weatherapp.databinding.FragmentHoursBinding

class Hours : Fragment() {
    private lateinit var binding: FragmentHoursBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentHoursBinding.inflate(inflater)
        return binding.root
    }
}