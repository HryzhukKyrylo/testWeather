package com.example.testweather.api

import com.example.testweather.BuildConfig
import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.HourlyWeatherResponse
import com.example.testweather.model.WeekWeatherResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET(WEEK_WEATHER)
    suspend fun getWeekWeather(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("units") units: String = "",
        @Query("exclude") exclude: String = "minutely,alerts,current,hourly",
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<WeekWeatherResponse>

    @GET(DAILY_WEATHER)
    suspend fun getDailyWeather(
        @Query("q") city_name: String,
        @Query("units") units: String = "",
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<DailyWeatherResponse>

    @GET(HOURLY_WEATHER)
    suspend fun getHourlyWeather(
        @Query("q") city_name: String,
        @Query("units") units: String = "",
        @Query("appid") appid: String = BuildConfig.API_KEY
    ): Response<HourlyWeatherResponse>


    companion object {
        private const val WEEK_WEATHER = "data/2.5/onecall?"
        private const val DAILY_WEATHER = "data/2.5/weather?"
        private const val HOURLY_WEATHER = "data/2.5/forecast?"

    }
}