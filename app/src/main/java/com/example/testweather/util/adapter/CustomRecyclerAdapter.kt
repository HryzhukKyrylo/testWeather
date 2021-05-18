package com.example.testweather.util.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.databinding.HeaderSectionBinding
import com.example.testweather.databinding.RecyclerItemBinding
import com.example.testweather.model.ItemData

interface Item {
    fun type(): Int
}

class CustomRecyclerAdapter(
    private val itemList: List<Item>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class HeaderViewHolder(private val binding: HeaderSectionBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData) {
            binding.textHeader.text = item.name
        }
    }

    class ItemViewHolder(private val binding: RecyclerItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: ItemData) {
            binding.textItem.text = item.name

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Const.HEADER_SECTION -> HeaderViewHolder(
                HeaderSectionBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
                else -> ItemViewHolder(
                RecyclerItemBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position] as ItemData
        return when (getItemViewType(position)) {
            Const.HEADER_SECTION -> (holder as HeaderViewHolder).bind(item)
            else -> (holder as ItemViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].type()) {
            0 -> Const.HEADER_SECTION
            else -> Const.ITEM_SECTION
        }
    }
}