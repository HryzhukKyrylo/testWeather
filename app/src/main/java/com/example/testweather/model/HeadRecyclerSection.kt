package com.example.testweather.model

import com.example.testweather.adapter.WeatherItem

data class HeadRecyclerSection(
    val selectedDay: String
) : WeatherItem()