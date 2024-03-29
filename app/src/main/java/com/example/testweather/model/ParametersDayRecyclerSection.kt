package com.example.testweather.model

import com.example.testweather.adapter.WeatherItem

data class ParametersDayRecyclerSection(
    val pressure: String,
    val humidity: String,
    val windSpeed: String,
    val clouds: String
) : WeatherItem()