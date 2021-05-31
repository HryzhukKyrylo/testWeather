package com.example.testweather.ui.weather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.const.Const
import com.example.testweather.databinding.ItemColumnDayBinding
import com.example.testweather.databinding.ItemDayBinding
import com.example.testweather.databinding.ItemNameOfDayBinding
import com.example.testweather.model.DailySection
import com.example.testweather.model.HeadRecyclerSection
import com.example.testweather.model.HourlySection
import com.example.testweather.model.ParametersDayRecyclerSection
import com.example.testweather.util.IconHelper
import com.example.testweather.util.getDateString
import com.example.testweather.util.getHourData

abstract class WeatherItem

class CustomRecyclerAdapter(
    private var itemList: List<WeatherItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var listener: OnItemClickedListener

    class DailyViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: ParametersDayRecyclerSection) {
            binding.twPressure.text = item.pressure + " hPa"
            binding.twHumidity.text = item.humidity + " %"
            binding.twWind.text = item.windSpeed
            binding.twClouds.text = item.clouds + " %"
        }
    }

    class WeekViewHolder(
        private val listener: OnItemClickedListener,
        private val binding: ItemColumnDayBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: DailySection) {
            if (absoluteAdapterPosition == 0) {
                val param = binding.root.layoutParams as ViewGroup.MarginLayoutParams
                param.setMargins(5, 15, 5, 10)
                binding.root.cardElevation = 8f
                binding.root.elevation = 12f
                binding.root.layoutParams = param
            }
            binding.root.setOnClickListener {
                listener.onEntryClicked(item.dt)
            }
            binding.twTextInItem.text = getDateString(item.dt)
            binding.iwImageInItem.setImageResource(IconHelper.getIconResource(item.weather[0].icon))
            binding.twTempInItem.text = item.temp.dayText
        }
    }

    class HourViewHolder(private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: HourlySection) {
            binding.twTextInItem.text = getHourData(item.dt)
            binding.iwImageInItem.setImageResource(IconHelper.getIconResource(item.weather[0].icon))
            binding.twTempInItem.text = item.main.temp_text
        }
    }

    class HeadViewHolder(private val binding: ItemNameOfDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HeadRecyclerSection) {
            binding.twDataOfDay.text = item.selectedDay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Const.DAILY_SECTION -> DailyViewHolder(
                ItemDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.WEEK_SECTION -> WeekViewHolder(
                listener,
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.HOUR_SECTION -> HourViewHolder(
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.HEADER_SECTION -> HeadViewHolder(
                ItemNameOfDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> {
                throw IllegalArgumentException("Invalid type of data")
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position]
        when (holder) {
            is DailyViewHolder -> holder.bind(item as ParametersDayRecyclerSection)
            is WeekViewHolder -> holder.bind(item as DailySection)
            is HourViewHolder -> holder.bind(item as HourlySection)
            is HeadViewHolder -> holder.bind(item as HeadRecyclerSection)
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position]) {
            is ParametersDayRecyclerSection -> Const.DAILY_SECTION
            is DailySection -> Const.WEEK_SECTION
            is HourlySection -> Const.HOUR_SECTION
            is HeadRecyclerSection -> Const.HEADER_SECTION
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    fun setListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    interface OnItemClickedListener {
        fun onEntryClicked(data: Int)
    }
}