package com.example.testweather.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.R
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
        // all strings move to resource as "%1$s ..."
        // Done
        fun bind(item: ParametersDayRecyclerSection) {
            binding.twPressure.text =
                item.pressure + binding.root.context.resources.getString(R.string.hPa)
            binding.twHumidity.text =
                item.humidity + binding.root.context.resources.getString(R.string.procent)
            binding.twWind.text = item.windSpeed
            binding.twClouds.text =
                item.clouds + binding.root.context.resources.getString(R.string.procent)
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
            binding.iwImageInItem.setImageResource(IconHelper.getIconResource(item.weather.first().icon))
            binding.twTempInItem.text = item.temp.dayText
        }
    }

    class HourViewHolder(private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: HourlySection) {
            binding.twTextInItem.text = getHourData(item.dt)
            binding.iwImageInItem.setImageResource(IconHelper.getIconResource(item.weather.first().icon))
            binding.twTempInItem.text = item.main.temp_text
        }
    }

    class HeadViewHolder(private val binding: ItemNameOfDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: HeadRecyclerSection) {
            binding.twDataOfDay.text = item.selectedDay
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder =
        when (viewType) {
            DAILY_SECTION -> DailyViewHolder(
                ItemDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            WEEK_SECTION -> WeekViewHolder(
                listener,
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            HOUR_SECTION -> HourViewHolder(
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            HEADER_SECTION -> HeadViewHolder(
                ItemNameOfDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> {
                throw IllegalArgumentException("Invalid type of data")
            }
        }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) = when (holder) {
        is DailyViewHolder -> holder.bind(itemList[position] as ParametersDayRecyclerSection)
        is WeekViewHolder -> holder.bind(itemList[position] as DailySection)
        is HourViewHolder -> holder.bind(itemList[position] as HourlySection)
        is HeadViewHolder -> holder.bind(itemList[position] as HeadRecyclerSection)
        else -> {
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int) =
        when (itemList[position]) {
            is ParametersDayRecyclerSection -> DAILY_SECTION
            is DailySection -> WEEK_SECTION
            is HourlySection -> HOUR_SECTION
            is HeadRecyclerSection -> HEADER_SECTION
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }

    fun setListener(listener: OnItemClickedListener) {
        this.listener = listener
    }

    interface OnItemClickedListener {
        fun onEntryClicked(data: Int)
    }

    companion object {
        const val DAILY_SECTION = 1
        const val WEEK_SECTION = 5
        const val HOUR_SECTION = 6
        const val HEADER_SECTION = 8

    }
}