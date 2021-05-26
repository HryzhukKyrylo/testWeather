package com.example.testweather.util

import android.annotation.SuppressLint
import java.text.SimpleDateFormat


    @SuppressLint("SimpleDateFormat")
    private val simpleDateFormat = SimpleDateFormat("EEE, MMMM dd")
// for test
//    private val simpleDate2Format = SimpleDateFormat("EEE, MMMM dd HH:mm")
    @SuppressLint("SimpleDateFormat")
    private val simpleHourlyFormat = SimpleDateFormat("EEE, dd MMMM yyyy")
    @SuppressLint("SimpleDateFormat")
    private val simpleDayFormat = SimpleDateFormat("MMMM dd ")
    private val simpleHourFormat = SimpleDateFormat("HH:mm")

    fun getDateString(time: Int): String = simpleDateFormat.format(time * 1000L)
// for test
//    fun getDate2String(time: Int): String = simpleDate2Format.format(time * 1000L)
    fun getDateDayString(time: Int): String = simpleHourlyFormat.format(time * 1000L)
    fun getSelectedData(time: Int): String = simpleDayFormat.format(time * 1000L)
    fun getHourData(time: Int): String = simpleHourFormat.format(time * 1000L)

