package com.example.testweather.repository

import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.HourlyWeatherResponse
import com.example.testweather.model.WeekWeatherResponse
import retrofit2.Response

interface WeatherRepository {
    suspend fun getWeekWeather(
        lat: Double,
        lon: Double,
        units: String
    ): Response<WeekWeatherResponse>

    suspend fun getDailyWeather(city_name: String, units: String): Response<DailyWeatherResponse>

    suspend fun getHourlyWeather(city_name: String, units: String): Response<HourlyWeatherResponse>
}