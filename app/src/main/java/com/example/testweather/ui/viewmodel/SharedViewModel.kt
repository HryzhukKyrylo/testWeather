package com.example.testweather.ui.viewmodel

import android.location.Geocoder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.testweather.const.Const
import com.example.testweather.model.DayCardSection
import com.example.testweather.model.HeadRecyclerSection
import com.example.testweather.model.ParametersDayRecyclerSection
import com.example.testweather.repository.WeatherRepository
import com.example.testweather.ui.weather.adapter.WeatherItem
import com.example.testweather.util.getDateDayString
import com.example.testweather.util.getSelectedData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SharedViewModel @Inject constructor(
    private val weatherRepository: WeatherRepository
) : ViewModel() {
    private val dayCard = MutableLiveData<DayCardSection>()
    private val listRecycler = MutableLiveData<List<WeatherItem>>()

    val dayCardSection = dayCard
    val listForRecycler = listRecycler

    //settings
    private var screen = MutableLiveData<Int>()
    val startScreen = screen
    var setSelectCity = ""
    private var units: String? = null
    private var unitsText = ""
    private var windSpeedText = ""

    // kyiv
    private var latKyiv = 50.4501
    private var lonKyiv = 30.5234
    private var city = "kyiv"

    fun getDailyWeather() = viewModelScope.launch {
        weatherRepository.getDailyWeather(city, units ?: "").let { response ->
            if (response.isSuccessful) {
                dayCard.value = response.body()?.let {
                    DayCardSection(
                        textCity = it.name,
                        imageIcon = it.weather[0].icon,
                        textClouds = it.weather[0].main,
                        textTemp = it.main.temp.toInt().toString() + unitsText
                    )
                }
            } else {
                Log.i("TAG_VIEW_MODEL", "getDailyWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getDayWeather() = viewModelScope.launch {
        weatherRepository.getDailyWeather(city, units ?: "").let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    ParametersDayRecyclerSection(
                        pressure = it.main.pressure.toString(),
                        humidity = it.main.humidity.toString(),
                        windSpeed = it.wind.speed.toString() + windSpeedText,
                        clouds = it.clouds.all.toString()
                    )
                }?.let { listRecycler.value = listOf<WeatherItem>(it) }
            } else {
                Log.i("TAG_VIEW_MODEL", "getDailyWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getWeather() {
        when (startScreen.value) {
            Const.WEEK_SECTION -> getWeekWeather()
            Const.THREE_DAY_SECTION -> getWeekWeather()
            Const.DAILY_SECTION -> getDayWeather()

        }
    }

    fun getWeekWeather() = viewModelScope.launch {
        weatherRepository.getWeekWeather(latKyiv, lonKyiv, units ?: "").let { response ->
            if (response.isSuccessful) {

                val list = screen.value?.let {
                    response.body()?.dailySection?.take(it)?.apply {
                        this.map { it.temp.dayText = it.temp.day.toInt().toString() + unitsText }
                    }
                }
                listRecycler.value = list!!
            } else {
                Log.i("TAG_VIEW_MODEL", "getWeekWeather: ${response.errorBody().toString()}")
            }
        }
    }

    fun getHourlyWeather(selectedData: Int) = viewModelScope.launch {

        weatherRepository.getHourlyWeather(city_name = city, units = units ?: "").let { response ->
            if (response.isSuccessful) {
                val data = getSelectedData(selectedData)
                val list = response.body()?.list?.filter { element ->
                    getSelectedData(element.dt) == data
                }?.apply {
                    this.map { it.main.temp_text = it.main.temp.toInt().toString() + unitsText }
                }

                val items = ParametersDayRecyclerSection(
                    pressure = list?.first()?.main?.pressure.toString(),
                    humidity = list?.first()?.main?.humidity.toString(),
                    windSpeed = list?.first()?.wind?.speed.toString() + windSpeedText,
                    clouds = list?.first()?.clouds?.all.toString()
                )
                val listt = mutableListOf<WeatherItem>()
                listt.add(HeadRecyclerSection(selectedDay = getDateDayString(selectedData)))
                listt.addAll(list!!)
                listt.add(items)
                listRecycler.value = listt


            } else {
                Log.i("TAG_VIEW_MODEL", "getHourlyWeather: ${response.errorBody().toString()}")
            }
        }
    }
//    fun searchCity(str: String, geocoder: Geocoder) :String{
//        geocoder.getFromLocationName(str, 1).apply {
//
//        }
//    }

    fun initUnits(unit: String) {
        units = unit
    }

    fun initUnitsText(units: String) {
        unitsText = units
    }

    fun initWindSpeed(speed: String) {
        windSpeedText = speed
    }

    fun setScreen(int: Int) {
        screen.postValue(int)
    }
}