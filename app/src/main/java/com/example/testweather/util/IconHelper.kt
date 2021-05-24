package com.example.testweather.util

import com.example.testweather.R

object IconHelper {
    fun getIconResource(imageIcon: String): Int =
        when (imageIcon) {
            "01d" -> R.drawable.ic_sun
            "02d" -> R.drawable.ic_cloudysun
            "03d" -> R.drawable.ic_cloudy
            "04d" -> R.drawable.ic_cloudy
            "09d" -> R.drawable.ic_rain
            "10d" -> R.drawable.ic_rain
            "11d" -> R.drawable.ic_thunder
            "13d" -> R.drawable.ic_snow
            "50d" -> R.drawable.ic_fog

            else ->R.drawable.ic_cloudysun

    }
}