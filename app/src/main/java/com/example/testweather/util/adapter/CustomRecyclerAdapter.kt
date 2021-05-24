package com.example.testweather.util.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.const.Const
import com.example.testweather.databinding.ItemColumnDayBinding
import com.example.testweather.databinding.ItemDayBinding
import com.example.testweather.databinding.ItemNameOfDayBinding
import com.example.testweather.model.Daily
import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.HeadSection
import com.example.testweather.model.Hourly
import com.example.testweather.util.IconHelper
import com.example.testweather.util.getDateHourlyString
import com.example.testweather.util.getDateString
import com.example.testweather.util.getSelectedData

abstract class WeatherItem

class CustomRecyclerAdapter(
    val context: Context,
    private var itemList: List<WeatherItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private lateinit var listener: OnItemClickedListener


    class DailyViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyWeatherResponse) {
            binding.twPressure.text = item.main.pressure.toString()
            binding.twHumidity.text = item.main.humidity.toString()
            binding.twWind.text = item.wind.speed.toString()
            binding.twClouds.text = item.clouds.all.toString()
        }
    }

    class WeekViewHolder(
        val listener: OnItemClickedListener,
        val context: Context,
        private val binding: ItemColumnDayBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {

        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: Daily) {
            binding.twTextInItem.text = getDateString(item.dt)
            binding.twTextInItem.setOnClickListener {
                listener?.onEntryClicked(getSelectedData(item.dt))
            }
            binding.iwImageInItem.setImageResource(IconHelper.getIconResource(item.weather[0].icon))
            binding.twTempInItem.text = item.temp.day.toString() + " 'C"

        }
    }

    class HourlyViewHolder(
        private val binding: ItemColumnDayBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: Hourly) {
            binding.twTextInItem.text = getDateHourlyString(item.dt)
            binding.iwImageInItem.setImageResource(IconHelper.getIconResource(item.weather[0].icon))
            binding.twTempInItem.text = item.temp.toString() + " 'C"
        }
    }
    class DayViewHolder(
        private val binding: ItemNameOfDayBinding
    ) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: HeadSection) {
            binding.twDataOfDay.text = item.str
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
                context,
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.HOURLY_SECTION -> HourlyViewHolder(
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.HEADER_SECTION -> DayViewHolder(
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
            is DailyViewHolder -> holder.bind(item as DailyWeatherResponse)
            is WeekViewHolder -> holder.bind(item as Daily)
            is HourlyViewHolder -> holder.bind(item as Hourly)
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        val item = itemList[position]
        return when {
            item is DailyWeatherResponse -> Const.DAILY_SECTION
            item is Daily -> Const.WEEK_SECTION
            item is Hourly -> Const.HOURLY_SECTION
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }

    fun setListener(listener: OnItemClickedListener) {
        this.listener = listener
    }


    interface OnItemClickedListener {
        fun onEntryClicked(data: String)
    }
}