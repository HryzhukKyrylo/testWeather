package com.example.testweather.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.const.Const.CUSTOM_PREF_NAME
import com.example.testweather.databinding.FragmentWeatherScreenBinding
import com.example.testweather.ui.viewmodel.SharedViewModel
import com.example.testweather.util.adapter.CustomRecyclerAdapter
import com.example.testweather.util.adapter.WeatherItem
import com.example.testweather.util.preference.PreferenceHelper
import com.example.testweather.util.preference.PreferenceHelper.screen
import com.example.testweather.util.preference.PreferenceHelper.units
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen) {
    private lateinit var binding: FragmentWeatherScreenBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerAdapter: CustomRecyclerAdapter
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefs = PreferenceHelper.customPreference(requireContext(), CUSTOM_PREF_NAME)
        sharedViewModel.screen.postValue(prefs.screen)
//        sharedViewModel.units = prefs.units

        binding = FragmentWeatherScreenBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    override fun onResume() {
        super.onResume()
        sharedViewModel.screen.observe(viewLifecycleOwner, {
            startScreen(it)
        })
    }

    private fun startScreen(scr: Int) {
        Toast.makeText(requireContext(), "${prefs.screen}", Toast.LENGTH_SHORT).show()

        when (scr) {
            Const.DAILY_SECTION -> {
                sharedViewModel.getDailyWeather()
                sharedViewModel.dailyWeather.observe(viewLifecycleOwner, {

                    initCardView(
                        it.timezone.toString(),
                        it.weather[0].icon,
                        it.weather[0].main,
                        it.main.temp.toString()
                    )
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(),listOf<WeatherItem>(it))
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        adapter = recyclerAdapter
                    }
                })
            }
            Const.THREE_DAY_SECTION -> {
                sharedViewModel.getThreeDaysWeather(sharedViewModel.units)
                sharedViewModel.threeDaysWeather.observe(viewLifecycleOwner, {
                    initCardView(
                        it.timezone,
                        it.current.weather[0].icon,
                        it.current.weather[0].main,
                        it.current.temp.toString()
                    )
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(),it.daily.take(3))
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        adapter = recyclerAdapter
                    }
                })
            }
            Const.WEEK_SECTION -> {
                sharedViewModel.getWeekWeather()
                sharedViewModel.weekWeather.observe(viewLifecycleOwner, {
                    initCardView(
                        it.timezone,
                        it.current.weather[0].icon,
                        it.current.weather[0].main,
                        it.current.temp.toString()
                    )
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(),it.daily)
                    binding.recyclerView.apply {
                        layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                        adapter = recyclerAdapter
                    }
                })
            }
            else -> {
                Toast.makeText(
                    requireContext(),
                    "${prefs.screen} - HUSTON, WE HAVE A PROBLEM!",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun initCardView(
        textCity: String,
        imageIcon: String,
        textClouds: String,
        textTemp: String
    ) {
        binding.twCity.text = textCity
        when (imageIcon) {
            "01d" -> binding.imageCard.setImageResource(R.drawable.ic_sun)
            "02d" -> binding.imageCard.setImageResource(R.drawable.ic_cloudysun)
            "03d" -> binding.imageCard.setImageResource(R.drawable.ic_cloudy)
            "04d" -> binding.imageCard.setImageResource(R.drawable.ic_cloudy)
            "09d" -> binding.imageCard.setImageResource(R.drawable.ic_rain)
            "10d" -> binding.imageCard.setImageResource(R.drawable.ic_rain)
            "11d" -> binding.imageCard.setImageResource(R.drawable.ic_thunder)
            "13d" -> binding.imageCard.setImageResource(R.drawable.ic_snow)
            "50d" -> binding.imageCard.setImageResource(R.drawable.ic_fog)

            else -> binding.imageCard.setImageResource(R.drawable.ic_cloudysun)

        }
        binding.twCardName.text = textClouds
        binding.twCardTemp.text = textTemp
    }

    private fun initListeners() {
        binding.btSettings.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.navigateWeatherToSettingsScreen)
        }
    }

}
