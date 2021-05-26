package com.example.testweather.util.preference

import android.content.Context
import android.content.SharedPreferences
import com.example.testweather.R
import com.example.testweather.const.Const

object PreferenceHelper {
    const val WEATHER_UNITS = "UNITS"
    const val WEATHER_CELSIUS = "CELSIUS"
    const val WEATHER_FAHRENHEIGHT = "FAHRENHEIGHT"
    const val WEATHER_DAY = "DAY"
    const val WEATHER_M_S = "M_S"
    const val WEATHER_UNITS_TEXT = "UNITS_TEXT"
    const val WEATHER_WIND_SPEED = "SPEED_TEXT"
    const val WEATHER_M_H = "M_H"
    const val WEATHER_CITY = "CITY"

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
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

    var SharedPreferences.city
        get() = getString(WEATHER_CITY, "")
        set(value) {
            editMe {
                it.putString(WEATHER_CITY, value)
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
    var SharedPreferences.fahrenheitSet
        get() = getBoolean(WEATHER_FAHRENHEIGHT, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_FAHRENHEIGHT, value)
            }
        }

    var SharedPreferences.celsiusSet
        get() = getBoolean(WEATHER_CELSIUS, true)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_CELSIUS, value)
            }
        }

    var SharedPreferences.m_sSet
        get() = getBoolean(WEATHER_M_S, true)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_M_S, value)
            }
        }
    var SharedPreferences.m_hSet
        get() = getBoolean(WEATHER_M_H, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_M_H, value)
            }
        }
    var SharedPreferences.citySet
        get() = getBoolean(WEATHER_CITY, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_CITY, value)
            }
        }
}