package com.example.testweather.ui.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweather.model.DailySection
import com.example.testweather.model.DayCardSection
import com.example.testweather.model.HourlySection
import com.example.testweather.model.ParametersDayRecyclerSection
import com.example.testweather.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val dayCard = MutableLiveData<DayCardSection>()
    private val parametersDay = MutableLiveData<ParametersDayRecyclerSection>()
    private val days = MutableLiveData<List<DailySection>>()
    private val hourly = MutableLiveData<List<HourlySection>>()


    val hourlySection = hourly
    val dayCardSection = dayCard
    val parametersDaySection = parametersDay
    val dailySection = days

    private var screen = MutableLiveData<Int>()
    val startScreen = screen
    var setSelectCity = ""
    var units: String? = null
    var unitsText = ""
//    var setMs = false
//    var setMh = false // * 2.237


    // kyiv
    private val latKyiv = 50.4501
    private val lonKyiv = 30.5234
    private val city = "kyiv"
//    val str_c = " °C"
//    val str_f = "°F"

    fun setScreen(int: Int) {
        screen.postValue(int)
    }

    fun getDailyWeather() = viewModelScope.launch {
        weatherRepository.getDailyWeather(city, units ?: "").let { response ->
            if (response.isSuccessful) {
                dayCard.value = response.body()?.let {
                    DayCardSection(
                        textCity = it.name,
                        imageIcon = it.weather[0].icon,
                        textClouds = it.weather[0].main,
                        textTemp = it.main.temp.toString() + unitsText
                    )
                }
                parametersDay.value = response.body()?.let {
                    ParametersDayRecyclerSection(
                        pressure = it.main.pressure.toString(),
                        humidity = it.main.humidity.toString(),
                        windSpeed = it.wind.speed.toString() ,
                        clouds = it.clouds.all.toString()
                    )
                }
            } else {
                Log.i("TAG_VIEW_MODEL", "getDailyWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getWeekWeather() = viewModelScope.launch {
        weatherRepository.getWeekWeather(latKyiv, lonKyiv, units ?: "").let { response ->
            if (response.isSuccessful) {
                dayCard.value = response.body()?.let {
                    DayCardSection(
                        textCity = it.timezone,
                        imageIcon = it.current.weather[0].icon,
                        textClouds = it.current.weather[0].main,
                        textTemp = it.current.temp.toString() + unitsText
                    )
                }
                parametersDay.value = response.body()?.let {
                    ParametersDayRecyclerSection(
                        pressure = it.dailySection[0].pressure.toString(),
                        humidity = it.dailySection[0].humidity.toString(),
                        windSpeed = it.dailySection[0].wind_speed.toString() ,
                        clouds = it.dailySection[0].clouds.toString()
                    )
                }
                days.value = screen.value?.let {
                    response.body()?.dailySection?.take(it)?.apply {
                        this.map{ it.temp.dayText = it.temp.day.toString() + unitsText }
                    }
                }

            } else {
                Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
            }
        }
    }


    fun getHourlyWeather() = viewModelScope.launch {
        weatherRepository.getHourlyWeather(city_name = city, units = units ?: "")
            .let { response ->
                if (response.isSuccessful) {
                    hourly.value = response.body()?.list?.apply {
                        this.map{ it.main.temp_text = it.main.temp.toString() + unitsText }
                    }

                } else {
                    Log.i("TAG_VIEW_MODEL", "getHourlyWeather: ${response.errorBody().toString()}")
                }
            }
    }
}