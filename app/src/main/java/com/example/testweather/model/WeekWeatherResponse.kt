package com.example.testweather.model

import com.example.testweather.ui.weather.adapter.WeatherItem
import com.google.gson.annotations.SerializedName


data class WeekWeatherResponse(
    @SerializedName("daily") val dailySection: List<DailySection>
) : WeatherItem()

data class DailySection(
    val dt: Int,
    val temp: Temp,
    val weather: List<Weather>,
) : WeatherItem()


data class Temp(
    val day: Double,
    var dayText: String,
)


