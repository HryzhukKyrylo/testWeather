package com.example.testweather.util.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.databinding.ItemColumnDayBinding
import com.example.testweather.databinding.ItemDayBinding
import com.example.testweather.databinding.RecyclerItemBinding
import com.example.testweather.model.DailyResponse
import com.example.testweather.model.WeekWeatherResponse

interface Item {
    fun type(): Int
}

class CustomRecyclerAdapter(
    private val itemList: List<Item>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    class DailyViewHolder(private val binding: ItemDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: DailyResponse) {
            binding.twPressure.text = item.main.pressure.toString()
            binding.twHumidity.text = item.main.humidity.toString()
            binding.twWind.text = item.wind.speed.toString()
            binding.twClouds.text = item.clouds.all.toString()
        }
    }

    class ThreeDayViewHolder(private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        @SuppressLint("SetTextI18n")
        @RequiresApi(Build.VERSION_CODES.O)
        fun bind(item: WeekWeatherResponse) {
            val data = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                java.time.Instant.ofEpochSecond(item.daily[0].dt.toLong())
            )
                .format(java.time.Instant.ofEpochSecond(item.daily[0].dt.toLong()))
            binding.twTextInItem.text = data
            binding.twTextInItem.setOnClickListener {
                Log.i("TAG_VIEW_HOLDER", "bind: $data")
            }

            when (item.daily[0].weather[0].icon) {
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
//        holder.imageView?.setImageResource(images[position])
            binding.twTempInItem.text = item.daily[0].temp.day.toString() + " 'C"
        }
    }

    class WeekViewHolder(private val binding: ItemColumnDayBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: WeekWeatherResponse) {
            val data = java.time.format.DateTimeFormatter.ISO_INSTANT.format(
                java.time.Instant.ofEpochSecond(item.daily[0].dt.toLong())
            )
                .format(java.time.Instant.ofEpochSecond(item.daily[0].dt.toLong()))
            binding.twTextInItem.text = data
            binding.twTextInItem.setOnClickListener {
                Log.i("TAG_VIEW_HOLDER", "bind: $data")
            }

            when (item.daily[0].weather[0].icon) {
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
//        holder.imageView?.setImageResource(images[position])
            binding.twTempInItem.text = item.daily[0].temp.day.toString() + " 'C"

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            Const.DAILY_SECTION -> DailyViewHolder(
                ItemDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            Const.THREE_DAY_SECTION -> ThreeDayViewHolder(
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
            else -> ThreeDayViewHolder(
                ItemColumnDayBinding.inflate(
                    LayoutInflater.from(parent.context), parent, false
                )
            )
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val item = itemList[position] as DailyResponse
        return when (getItemViewType(position)) {
            Const.DAILY_SECTION -> (holder as DailyViewHolder).bind(item)
            else -> (holder as ThreeDayViewHolder).bind(item)
        }
    }

    override fun getItemCount(): Int = itemList.size

    override fun getItemViewType(position: Int): Int {
        return when (itemList[position].type()) {
            1 -> Const.DAILY_SECTION
            3 -> Const.THREE_DAY_SECTION
            else -> Const.WEEK_SECTION
        }
    }
}