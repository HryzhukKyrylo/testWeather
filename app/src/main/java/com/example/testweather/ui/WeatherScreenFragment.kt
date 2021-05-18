package com.example.testweather.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweather.R
import com.example.testweather.databinding.FragmentWeatherScreenBinding
import com.example.testweather.ui.viewmodel.RecyclerViewModel
import com.example.testweather.util.adapter.CustomRecyclerAdapter


class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen) {
    private lateinit var binding: FragmentWeatherScreenBinding
    private lateinit var recyclerViewModel : RecyclerViewModel
    private lateinit var recyclerAdapter : CustomRecyclerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWeatherScreenBinding.inflate(inflater, container,false)
        recyclerViewModel = RecyclerViewModel()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
    }

    private fun setupRecyclerData(){
        recyclerAdapter = CustomRecyclerAdapter(recyclerViewModel.getData())
        binding.recyclerView.apply {
            layoutManager = LinearLayoutManager(requireContext(),LinearLayoutManager.VERTICAL,false)
            adapter = recyclerAdapter
        }
    }

    override fun onResume() {
        super.onResume()
        recyclerViewModel.setData()
        setupRecyclerData()
    }
}