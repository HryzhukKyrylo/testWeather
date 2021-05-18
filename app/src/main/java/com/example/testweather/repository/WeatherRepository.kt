package com.example.testweather.repository

import com.example.testweather.model.DailyResponse
import com.example.testweather.model.WeekWeatherResponse
import retrofit2.Response


interface WeatherRepository {
    suspend fun getWeekWeather(lat: Double, lon: Double): Response<WeekWeatherResponse>
    suspend fun getDailyWeather(city_name: String): Response<DailyResponse>
}