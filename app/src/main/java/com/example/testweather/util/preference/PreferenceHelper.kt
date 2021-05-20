package com.example.testweather.util.preference

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager

object PreferenceHelper {
    val WEATHER_FAHRENHEIT = "FAHRENHEIT"
    val WEATHER_CELSIUS = "CELSIUS"
    val WEATHER_DAILY = "DAILY"
    val WEATHER_THREE_DAY = "THREE_DAY"
    val WEATHER_WEEK = "WEEK"
    val WEATHER_M_S = "M_S"
    val WEATHER_M_H = "M_H"
    val WEATHER_CITY = "CITY"

    fun defaultPreference(context: Context): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

    fun customPreference(context: Context, name: String): SharedPreferences = context.getSharedPreferences(name, Context.MODE_PRIVATE)

    inline fun SharedPreferences.editMe(operation: (SharedPreferences.Editor) -> Unit) {
        val editMe = edit()
        operation(editMe)
        editMe.apply()
    }

    var SharedPreferences.fahrenheit
        get() = getString(WEATHER_FAHRENHEIT, "")
        set(value) {
            editMe {
                it.putString(WEATHER_FAHRENHEIT, value)
            }
        }

    var SharedPreferences.celsius
        get() = getString(WEATHER_CELSIUS, "")
        set(value) {
            editMe {
                it.putString(WEATHER_CELSIUS, value)
            }
        }

    var SharedPreferences.screen
        get() = getInt(WEATHER_WEEK, 0)
        set(value) {
            editMe {
                it.putInt(WEATHER_WEEK, value)
            }
        }
    var SharedPreferences.m_s
        get() = getString(WEATHER_M_S, "")
        set(value) {
            editMe {
                it.putString(WEATHER_M_S, value)
            }
        }
    var SharedPreferences.m_h
        get() = getString(WEATHER_M_H, "")
        set(value) {
            editMe {
                it.putString(WEATHER_M_H, value)
            }
        }
    var SharedPreferences.city
        get() = getString(WEATHER_CITY, "")
        set(value) {
            editMe {
                it.putString(WEATHER_CITY, value)
            }
        }

    var SharedPreferences.clearValues
        get() = run { }
        set(value) {
            editMe {
                it.clear()
            }
        }

    // initSettings
    var SharedPreferences.fahrenheitSet
        get() = getBoolean(WEATHER_FAHRENHEIT, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_FAHRENHEIT, value)
            }
        }

    var SharedPreferences.celsiusSet
        get() = getBoolean(WEATHER_CELSIUS, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_CELSIUS, value)
            }
        }

    var SharedPreferences.screenSet
        get() = getBoolean(WEATHER_WEEK, false)
        set(value) {
            editMe {
                it.putBoolean(WEATHER_WEEK, value)
            }
        }
    var SharedPreferences.m_sSet
        get() = getBoolean(WEATHER_M_S, false)
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