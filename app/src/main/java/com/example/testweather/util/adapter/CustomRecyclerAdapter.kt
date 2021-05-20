package com.example.testweather.util.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.databinding.ItemColumnDayBinding
import com.example.testweather.databinding.ItemDayBinding
import com.example.testweather.model.Daily
import com.example.testweather.model.DailyWeatherResponse

abstract class WeatherItem

class CustomRecyclerAdapter(
    private val itemList: List<WeatherItem>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DailyViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyWeatherResponse) {
            binding.twPressure.text = item.main.pressure.toString()
            binding.twHumidity.text = item.main.humidity.toString()
            binding.twWind.text = item.wind.speed.toString()
            binding.twClouds.text = item.clouds.all.toString()
        }
    }

    class WeekViewHolder(private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        fun bind(item: Daily) {
            binding.twTextInItem.text = item.dt.toString()
            binding.twTextInItem.setOnClickListener {
                Log.i("TAG_VIEW_HOLDER", "bind: $item.current.dt.toString()")
            }

            when (item.weather[0].icon) {
                "01d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_sun)
                "02d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_cloudysun)
                "03d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_cloudy)
                "04d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_cloudy)
                "09d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_rain)
                "10d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_rain)
                "11d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_thunder)
                "13d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_snow)
                "50d" -> binding.iwImageInItem.setImageResource(R.drawable.ic_fog)

                else -> binding.iwImageInItem.setImageResource(R.drawable.ic_cloudysun)

            }
            binding.twTempInItem.text = item.temp.day.toString() + " 'C"

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
                ItemColumnDayBinding.inflate(
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
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        val item = itemList[position]
        return when {
            item is DailyWeatherResponse -> Const.DAILY_SECTION
            item is Daily -> Const.WEEK_SECTION
            else -> throw IllegalArgumentException("Invalid type of data $position")
        }
    }
}