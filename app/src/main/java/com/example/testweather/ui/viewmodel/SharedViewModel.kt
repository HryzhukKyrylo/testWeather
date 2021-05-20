package com.example.testweather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweather.const.Const
import com.example.testweather.model.DailyResponse
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
    private val daily = MutableLiveData<DailyResponse>()
    private val week = MutableLiveData<WeekWeatherResponse>()
    val dailyWeather = daily
    val weekWeather = week

    // kyiv
    private val latKyiv = 50.4501
    private val lonKyiv = 30.5234
    private val city = "kyiv"

    fun getDailyWeather() = viewModelScope.launch {
        weatherRepository.getDailyWeather(city).let { response ->
            if (response.isSuccessful) {
                withContext(Dispatchers.Main) {
                    daily.postValue(response.body()?.apply { type = Const.DAILY_SECTION })
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
                    week.postValue(response.body()?.apply { type = Const.WEEK_SECTION })
                }
            } else {
                Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
            }
        }

    }

}