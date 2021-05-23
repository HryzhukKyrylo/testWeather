package com.example.testweather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweather.const.Const
import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.ThreeDaysWeatherResponse
import com.example.testweather.model.WeekWeatherResponse
import com.example.testweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val daily = MutableLiveData<DailyWeatherResponse>()
    private val week = MutableLiveData<WeekWeatherResponse>()
    private val threeDays = MutableLiveData<ThreeDaysWeatherResponse>()
    val dailyWeather = daily
    val threeDaysWeather = threeDays
    val weekWeather = week

    var screen = MutableLiveData<Int>()
    var setSelectCity = ""
    var units = ""
    var setM_s = false
    var setM_H = false


    // kyiv
    private val latKyiv = 50.4501
    private val lonKyiv = 30.5234
    private val city = "kyiv"

    fun getDailyWeather() = viewModelScope.launch {
        weatherRepository.getDailyWeather(city).let { response ->
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    daily.postValue(response.body())
                }
            } else {
                Log.i("TAG_VIEW_MODEL", "getDailyWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getWeekWeather() = viewModelScope.launch {
        weatherRepository.getWeekWeather(latKyiv, lonKyiv).let { response ->
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    week.postValue(response.body())
                }
            } else {
                Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
            }
        }

    }
    fun getThreeDaysWeather(units: String) = viewModelScope.launch {
        weatherRepository.getThreeDaysWeather(lat = latKyiv, lon = lonKyiv,units = units).let { response ->
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    threeDays.postValue(response.body())
                }
            } else {
                Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
            }
        }

    }

}