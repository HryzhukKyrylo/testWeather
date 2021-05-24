package com.example.testweather.model

import com.google.gson.annotations.SerializedName

data class HourlyWeatherResponse (
    @SerializedName("list") val list : List<ListHourly>,
    )
data class ListHourly (

    @SerializedName("dt") val dt : Int,
    @SerializedName("main") val main : Main,
    @SerializedName("weather") val weather : List<Weather>,
    @SerializedName("clouds") val clouds : Clouds,
    @SerializedName("wind") val wind : Wind,
    @SerializedName("visibility") val visibility : Int,
    @SerializedName("pop") val pop : Int,
    @SerializedName("sys") val sys : Sys,
    @SerializedName("dt_txt") val dt_txt : String
)
