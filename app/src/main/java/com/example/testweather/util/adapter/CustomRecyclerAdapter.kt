package com.example.testweather.util.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.databinding.ItemColumnDayBinding
import com.example.testweather.databinding.ItemDayBinding
import com.example.testweather.model.Daily
import com.example.testweather.model.DailyWeatherResponse
import com.example.testweather.model.Hourly

abstract class WeatherItem

class CustomRecyclerAdapter(
     val context : Context,
     private var itemList : List<WeatherItem>

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

    class WeekViewHolder(
        val context: Context,
        private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: Daily) {
            val sdf = java.text.SimpleDateFormat("EEE, dd 'at' HH:mm:ss")

            binding.twTextInItem.text = sdf.format(item.dt)

            binding.twTextInItem.setOnClickListener {
                Toast.makeText(context, "${sdf.format(item.dt)}", Toast.LENGTH_SHORT).show()
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
    class HourlyViewHolder(
        val context: Context,
        private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n", "SimpleDateFormat")
        fun bind(item: Hourly) {
            val sdf = java.text.SimpleDateFormat("HH:mm:ss")

            binding.twTextInItem.text = sdf.format(item.dt)

            binding.twTextInItem.setOnClickListener {
                Toast.makeText(context, "${sdf.format(item.dt)}", Toast.LENGTH_SHORT).show()
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
            binding.twTempInItem.text = item.temp.toString() + " 'C"

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Const.DAILY_SECTION -> DailyViewHolder(
                ItemDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.WEEK_SECTION -> WeekViewHolder(context,
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
}