package com.example.testweather.model

import com.example.testweather.ui.weather.adapter.WeatherItem

data class HeadRecyclerSection (
    val selectedDay: String
        ) : WeatherItem()