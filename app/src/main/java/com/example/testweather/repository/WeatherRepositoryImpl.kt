package com.example.testweather.repository

import com.example.testweather.api.ApiService
import com.example.testweather.model.DailyResponse
import com.example.testweather.model.WeekWeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
    ) :WeatherRepository {
    override suspend fun getWeekWeather(lat:Double, lon:Double): Response<WeekWeatherResponse> =
        apiService.getWeekWeather(lat, lon)


    override suspend fun getDailyWeather(city_name: String): Response<DailyResponse> =
        apiService.getDailyWeather(city_name)
}