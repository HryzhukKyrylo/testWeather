package com.example.testweather.model

import com.example.testweather.util.adapter.WeatherItem
import com.google.gson.annotations.SerializedName

data class ThreeDaysWeatherResponse(
    @SerializedName("lat") val lat: Double,
    @SerializedName("lon") val lon: Double,
    @SerializedName("timezone") val timezone: String,
    @SerializedName("timezone_offset") val timezone_offset: Int,
    @SerializedName("current") val current: Current,
    @SerializedName("minutely") val minutely: List<Minutely>,
    @SerializedName("hourly") val hourly: List<Hourly>,
    @SerializedName("daily") val daily: List<Daily>
) : WeatherItem()

