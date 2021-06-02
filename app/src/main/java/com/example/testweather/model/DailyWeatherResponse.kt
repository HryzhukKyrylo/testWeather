package com.example.testweather.model

import com.example.testweather.adapter.WeatherItem

data class DailyWeatherResponse(
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val clouds: Clouds,
) : WeatherItem()

data class Main(
    val temp: Double,
    var temp_text: String,
    val pressure: Int,
    val humidity: Int
)

data class Wind(
    val speed: Double,
    var speedText: String,
)

data class Clouds(
    val all: Int
)

data class Weather(
    val main: String,
    val description: String,
    val icon: String
)