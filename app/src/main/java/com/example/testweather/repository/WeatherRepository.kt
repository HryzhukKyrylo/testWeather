package com.example.testweather.repository

import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.ThreeDaysWeatherResponse
import com.example.testweather.model.WeekWeatherResponse
import retrofit2.Response


interface WeatherRepository {
    suspend fun getWeekWeather(lat: Double, lon: Double): Response<WeekWeatherResponse>
    suspend fun getThreeDaysWeather(lat: Double, lon: Double, units: String): Response<ThreeDaysWeatherResponse>
    suspend fun getDailyWeather(city_name: String): Response<DailyWeatherResponse>
}