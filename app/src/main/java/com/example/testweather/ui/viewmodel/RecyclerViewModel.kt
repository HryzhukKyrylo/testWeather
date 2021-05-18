package com.example.testweather.ui.viewmodel

import androidx.lifecycle.ViewModel
import com.example.testweather.const.Const
import com.example.testweather.model.ItemData
import com.example.testweather.util.adapter.Item

class RecyclerViewModel: ViewModel() {
    private val item : MutableList<Item> = mutableListOf()

    fun setData(){
        item.add(ItemData(Const.HEADER_SECTION,"Name Section"))
        item.add(ItemData(Const.ITEM_SECTION,"Name 1"))
        item.add(ItemData(Const.ITEM_SECTION,"Name 2"))
        item.add(ItemData(Const.ITEM_SECTION,"Name 3"))

        item.add(ItemData(Const.HEADER_SECTION,"Address Section"))
        item.add(ItemData(Const.ITEM_SECTION,"Address 1"))
        item.add(ItemData(Const.ITEM_SECTION,"Address 2"))
        item.add(ItemData(Const.ITEM_SECTION,"Address 3"))
        item.add(ItemData(Const.ITEM_SECTION,"Address 4"))

        item.add(ItemData(Const.HEADER_SECTION,"Gender Section"))
        item.add(ItemData(Const.ITEM_SECTION,"Gender 1"))
        item.add(ItemData(Const.ITEM_SECTION,"Gender 2"))
        item.add(ItemData(Const.ITEM_SECTION,"Gender 3"))
        item.add(ItemData(Const.ITEM_SECTION,"Gender 4"))
    }
    fun getData():List<Item> = item
}