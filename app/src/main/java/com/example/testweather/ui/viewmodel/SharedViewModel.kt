package com.example.testweather.ui.viewmodel

import android.content.SharedPreferences
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
import com.example.testweather.util.preference.PreferenceHelper.citySearch
import com.example.testweather.util.preference.PreferenceHelper.latSearch
import com.example.testweather.util.preference.PreferenceHelper.lonSearch
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.util.*
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
    private val searchCity = MutableLiveData<String>()
    private val searchLat = MutableLiveData<Double>()
    private val searchLon = MutableLiveData<Double>()
    private var screen = MutableLiveData<Int>()

    private val startScreen = screen
    val city = searchCity
    val lat = searchLat
    val lon = searchLon
    private var units: String? = null
    private var unitsText = ""
    private var windSpeedText = ""
    private var milInHour = false

    // kyiv
//    private var latKyiv = 50.4501
//    private var lonKyiv = 30.5234
//    private var cityKiyv = "kyiv"


    fun getDayItemWeather() = viewModelScope.launch {
        val city = city.value ?: ""
        weatherRepository.getDailyWeather(city, units ?: "").let { response ->
            if (response.isSuccessful) {
                dayCard.value = response.body()?.let {
                    DayCardSection(
                        textCity = city,
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

    private fun getDayWeather() = viewModelScope.launch {
        val city = city.value ?: ""
        weatherRepository.getDailyWeather(city, units ?: "").let { response ->
            if (response.isSuccessful) {
                response.body()?.let {
                    ParametersDayRecyclerSection(
                        pressure = it.main.pressure.toString(),
                        humidity = it.main.humidity.toString(),
                        windSpeed = it.wind.speed.times(if (milInHour) 2.237 else 1.0)
                            .toString() + windSpeedText,
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
            Const.DAILY_SECTION -> getDayWeather()
            else -> getWeekWeather()
        }
    }

    private fun getWeekWeather() = viewModelScope.launch {
        val latForSearch = lat.value ?: 0.0
        val lonForSearch = lon.value ?: 0.0
        weatherRepository.getWeekWeather(latForSearch, lonForSearch, units ?: "")
            .let { response ->
                if (response.isSuccessful) {
                    val list = screen.value?.let {
                        response.body()?.dailySection?.take(it)?.apply {
                            this.map { section ->
                                section.temp.dayText =
                                    section.temp.day.toInt().toString() + unitsText
                            }
                        }
                    }
                    listRecycler.value = list!!
                } else {
                    Log.i(
                        "TAG_VIEW_MODEL",
                        "getWeekWeather: ${response.errorBody().toString()}"
                    )
                }
            }
    }

    fun getHourlyWeather(selectedData: Int) = viewModelScope.launch {
        city.value?.let { city ->
            weatherRepository.getHourlyWeather(city_name = city, units = units ?: "")
                .let { response ->
                    if (response.isSuccessful) {
                        val data = getSelectedData(selectedData)
                        val list = response.body()?.list?.filter { element ->
                            getSelectedData(element.dt) == data
                        }?.apply {
                            this.map {
                                it.main.temp_text = it.main.temp.toInt().toString() + unitsText
                            }
                        }
                        val items = ParametersDayRecyclerSection(
                            pressure = list?.first()?.main?.pressure.toString(),
                            humidity = list?.first()?.main?.humidity.toString(),
                            windSpeed = list?.first()?.wind?.speed?.times(if (milInHour) 2.237 else 1.0)
                                .toString() + windSpeedText,
                            clouds = list?.first()?.clouds?.all.toString()
                        )
                        val listt = mutableListOf<WeatherItem>()
                        listt.add(HeadRecyclerSection(selectedDay = getDateDayString(selectedData)))
                        listt.addAll(list!!)
                        listt.add(items)
                        listRecycler.value = listt


                    } else {
                        Log.i(
                            "TAG_VIEW_MODEL",
                            "getHourlyWeather: ${response.errorBody().toString()}"
                        )
                    }
                }
        }
    }

    fun searchCity(str: String, geocoder: Geocoder, prefs: SharedPreferences): Boolean {
        val result = geocoder.getFromLocationName(str.uppercase(Locale.getDefault()), 1)
        if (result.isNotEmpty()) {
            setCitySearch(str.uppercase(Locale.getDefault()))
            setLatSearch(result.first().latitude)
            setLonSearch(result.first().longitude)

            prefs.citySearch = str.uppercase(Locale.getDefault())
            prefs.latSearch = result.first().latitude.toString()
            prefs.lonSearch = result.first().longitude.toString()
        }

        return result.isNotEmpty()
    }

    fun initUnits(unit: String) {
        units = unit
    }

    fun setLatSearch(lat: Double) {
        searchLat.value = lat
    }

    fun setLonSearch(lon: Double) {
        searchLon.value = lon
    }

    fun setCitySearch(city: String) {
        searchCity.value = city
    }

    fun setMilInHour(bol: Boolean) {
        milInHour = bol
    }

    fun initUnitsText(units: String) {
        unitsText = units
    }

    fun initWindSpeed(speed: String) {
        windSpeedText = speed
    }

    fun setScreen(int: Int) {
        screen.value = int
    }
}