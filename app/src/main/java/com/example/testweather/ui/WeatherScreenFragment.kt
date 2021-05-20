package com.example.testweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweather.R
import com.example.testweather.databinding.FragmentWeatherScreenBinding
import com.example.testweather.ui.viewmodel.SharedViewModel
import com.example.testweather.util.adapter.CustomRecyclerAdapter
import com.example.testweather.util.adapter.Item
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen) {
    private lateinit var binding: FragmentWeatherScreenBinding
    private val sharedViewModel: SharedViewModel by viewModels()
    private lateinit var recyclerAdapter: CustomRecyclerAdapter

    private var screen: Int = 0
    private val args: WeatherScreenFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        screen = args.navArgs
        binding = FragmentWeatherScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    private fun setupRecyclerData() {

        sharedViewModel.getDailyWeather()
        sharedViewModel.dailyWeather.observe(viewLifecycleOwner, {
            recyclerAdapter = CustomRecyclerAdapter(listOf<Item>(it))
            binding.recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
                adapter = recyclerAdapter
            }
        })

    }

    override fun onResume() {
        super.onResume()
        setupRecyclerData()
    }
}