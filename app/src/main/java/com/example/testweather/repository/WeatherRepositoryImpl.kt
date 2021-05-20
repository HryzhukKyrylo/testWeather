package com.example.testweather.repository

import com.example.testweather.api.ApiService
import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.ThreeDaysWeatherResponse
import com.example.testweather.model.WeekWeatherResponse
import retrofit2.Response
import javax.inject.Inject

class WeatherRepositoryImpl @Inject constructor(
    private val apiService: ApiService
    ) :WeatherRepository {
    override suspend fun getWeekWeather(lat:Double, lon:Double): Response<WeekWeatherResponse> =
        apiService.getWeekWeather(lat, lon)

    override suspend fun getThreeDaysWeather(lat: Double, lon: Double): Response<ThreeDaysWeatherResponse> =
        apiService.getThreeDaysWeather(lat, lon)

    override suspend fun getDailyWeather(city_name: String): Response<DailyWeatherResponse> =
        apiService.getDailyWeather(city_name)
}