package com.example.testweather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.HourlyWeatherResponse
import com.example.testweather.model.ThreeDaysWeatherResponse
import com.example.testweather.model.WeekWeatherResponse
import com.example.testweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val daily = MutableLiveData<DailyWeatherResponse>()
    private val week = MutableLiveData<WeekWeatherResponse>()
    private val threeDays = MutableLiveData<ThreeDaysWeatherResponse>()
    private val hourly = MutableLiveData<HourlyWeatherResponse>()
    val dailyWeather = daily
    val threeDaysWeather = threeDays
    val weekWeather = week
    val hourlyList = hourly

    private var screen = MutableLiveData<Int>()
    val startScreen = screen
    var setSelectCity = ""
    var units: String? = null
    var setM_s = false
    var setM_H = false // * 2.237


    // kyiv
    private val latKyiv = 50.4501
    private val lonKyiv = 30.5234
    private val city = "kyiv"
    val str_c = "°C"
    val str_f = "°F"

    fun setScreen(int: Int) {
        screen.postValue(int)
    }

    fun getDailyWeather() = viewModelScope.launch {
        weatherRepository.getDailyWeather(city, units ?: "").let { response ->
            if (response.isSuccessful) {
                daily.value = response.body()
            } else {
                Log.i("TAG_VIEW_MODEL", "getDailyWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getWeekWeather() = viewModelScope.launch {
        weatherRepository.getWeekWeather(latKyiv, lonKyiv, units ?: "").let { response ->
            if (response.isSuccessful) {
                week.value = response.body()
            } else {
                Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getThreeDaysWeather() = viewModelScope.launch {
        weatherRepository.getThreeDaysWeather(lat = latKyiv, lon = lonKyiv, units = units ?: "")
            .let { response ->
                if (response.isSuccessful) {
                    threeDays.value = response.body()
                } else {
                    Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
                }
            }
    }

     fun getHourlyWeather()= viewModelScope.launch  {
        weatherRepository.getHourlyWeather(city_name = city, units = units ?: "")
            .let { response ->
                if (response.isSuccessful) {
                    hourly.value = response.body()
                } else {
                    Log.i("TAG_VIEW_MODEL", "getHourlyWeather: ${response.errorBody().toString()}")
                }
            }
    }
}