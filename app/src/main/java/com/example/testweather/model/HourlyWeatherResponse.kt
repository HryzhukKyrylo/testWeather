package com.example.testweather.model

import com.example.testweather.ui.weather.adapter.WeatherItem

data class HourlyWeatherResponse(
    val list: List<HourlySection>,
)

data class HourlySection(
    val dt: Int,
    val main: Main,
    val weather: List<Weather>,
    val clouds: Clouds,
    val wind: Wind,
) : WeatherItem()