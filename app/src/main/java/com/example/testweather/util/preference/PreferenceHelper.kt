package com.example.testweather.util.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.testweather.R
import com.example.testweather.const.Const

object PreferenceHelper {
    private const val WEATHER_UNITS = "UNITS"
    private const val WEATHER_CELSIUS = "CELSIUS"
    private const val WEATHER_FAHRENHEIGHT = "FAHRENHEIGHT"
    private const val WEATHER_DAY = "DAY"
    private const val WEATHER_M_S = "M_S"
    private const val WEATHER_UNITS_TEXT = "UNITS_TEXT"
    private const val WEATHER_WIND_SPEED = "SPEED_TEXT"
    private const val WEATHER_M_H = "M_H"
    private const val WEATHER_CITY = "CITY"
    private const val WEATHER_SET_CITY = "CITY_SET"
    private const val WEATHER_SET_LAT = "LAT_SET"
    private const val WEATHER_SET_LON = "LON_SET"


    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    private inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.units
        get() = getString(WEATHER_UNITS, "metric")
        set(value) {
            editMe {
                it.putString(WEATHER_UNITS, value)
            }
        }

    var SharedPreferences.screen
        get() = getInt(WEATHER_DAY, 1)
        set(value) {
            editMe {
                it.putInt(WEATHER_DAY, value)
            }
        }
    var SharedPreferences.units_text
        get() = getString(WEATHER_UNITS_TEXT, Const.CELSIUS_PREF)
        set(value) {
            editMe {
                it.putString(WEATHER_UNITS_TEXT, value)
            }
        }

    var SharedPreferences.citySearch
        get() = getString(WEATHER_SET_CITY, "default")
        set(value) {
            editMe {
                it.putString(WEATHER_SET_CITY, value)
            }
        }
    var SharedPreferences.latSearch
        get() = getString(WEATHER_SET_LAT, "0.0")
        set(value) {
            editMe {
                it.putString(WEATHER_SET_LAT, value)
            }
        }
    var SharedPreferences.lonSearch
        get() = getString(WEATHER_SET_LON, "0.0")
        set(value) {
            editMe {
                it.putString(WEATHER_SET_LON, value)
            }
        }

    var SharedPreferences.windSpeed
        get() = getInt(WEATHER_WIND_SPEED, R.string.mil_h)
        set(value) {
            editMe {
                it.putInt(WEATHER_WIND_SPEED, value)
            }
        }

    // initSettings
    var SharedPreferences.checkFahrenheit
        get() = getBoolean(WEATHER_FAHRENHEIGHT, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_FAHRENHEIGHT, value)
            }
        }

    var SharedPreferences.checkCelsius
        get() = getBoolean(WEATHER_CELSIUS, true)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_CELSIUS, value)
            }
        }

    var SharedPreferences.checkMilesInSeconds
        get() = getBoolean(WEATHER_M_S, true)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_M_S, value)
            }
        }
    var SharedPreferences.checkMilesInHour
        get() = getBoolean(WEATHER_M_H, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_M_H, value)
            }
        }
    var SharedPreferences.checkCity
        get() = getBoolean(WEATHER_CITY, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_CITY, value)
            }
        }
}