package com.example.testweather.model

import com.example.testweather.util.adapter.Item

class ItemData(
    val type: Int,
    val name: String
) : Item {
    override fun type(): Int = type
}
