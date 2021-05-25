package com.example.testweather.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


    //        private val simpleDateFormat = SimpleDateFormat("dd MMMM yyyy, HH:mm:ss", Locale.ENGLISH)
    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("EEE, MMMM dd")
    @SuppressLint("SimpleDateFormat")
    private val simpleHourlyFormat = SimpleDateFormat("EEE, MMMM dd , HH:mm")
    @SuppressLint("SimpleDateFormat")
    private val simpleDayFormat = SimpleDateFormat("EEE, MMMM dd ")
    private val simpleHourFormat = SimpleDateFormat("HH:mm")

    fun getDateString(time: Int): String = simpleDateFormat.format(time * 1000L)
    fun getDateHourlyString(time: Int): String = simpleHourlyFormat.format(time * 1000L)
    fun getSelectedData(time: Int): String = simpleDayFormat.format(time * 1000L)
    fun getHourData(time: Int): String = simpleHourFormat.format(time * 1000L)

