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
import com.example.testweather.util.IconHelper
import com.example.testweather.util.adapter.CustomRecyclerAdapter
import com.example.testweather.util.adapter.WeatherItem
import com.example.testweather.util.getSelectedData
import com.example.testweather.util.preference.PreferenceHelper
import com.example.testweather.util.preference.PreferenceHelper.screen
import com.example.testweather.util.preference.PreferenceHelper.units
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen), CustomRecyclerAdapter.OnItemClickedListener {
    private lateinit var binding: FragmentWeatherScreenBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerAdapter: CustomRecyclerAdapter
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        prefs = PreferenceHelper.customPreference(requireContext(), CUSTOM_PREF_NAME)
        sharedViewModel.setScreen(prefs.screen)
        sharedViewModel.units = prefs.units

        binding = FragmentWeatherScreenBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        sharedViewModel.startScreen.observe(viewLifecycleOwner, {
            startScreen(it)
        })
    }

    private fun startScreen(scr: Int) {
        when (scr) {
            Const.DAILY_SECTION -> {
                sharedViewModel.getDailyWeather()
                sharedViewModel.dailyWeather.observe(viewLifecycleOwner, {

                    initCardView(
                        it.name,
                        it.weather[0].icon,
                        it.weather[0].main,
                        it.main.temp.toString()
                    )
                    recyclerAdapter =
                        CustomRecyclerAdapter(requireContext(), listOf<WeatherItem>(it))
                    startRecycler()
                })
            }
            Const.THREE_DAY_SECTION -> {
                sharedViewModel.getWeekWeather()
                sharedViewModel.weekWeather.observe(viewLifecycleOwner, {
                    initCardView(
                        it.timezone,
                        it.current.weather[0].icon,
                        it.current.weather[0].main,
                        it.current.temp.toString()
                    )
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(), it.daily.take(3))
                    recyclerAdapter.setListener(this@WeatherScreenFragment)

                    startRecycler()
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
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(), it.daily.take(5))
                    recyclerAdapter.setListener(this@WeatherScreenFragment)
                   startRecycler()
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
    private fun startRecycler(){
        binding.recyclerView.apply {
            layoutManager =
                LinearLayoutManager(
                    requireContext(),
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = recyclerAdapter
            setHasFixedSize(true)
        }
    }

    private fun initCardView(
        textCity: String,
        imageIcon: String,
        textClouds: String,
        textTemp: String
    ) {
        binding.twCity.text = textCity
        binding.imageCard.setImageResource(IconHelper.getIconResource(imageIcon))
        binding.twCardName.text = textClouds
        binding.twCardTemp.text = textTemp
    }

    private fun initListeners() {
        binding.btSettings.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.navigateWeatherToSettingsScreen)
        }
    }

    override fun onEntryClicked(data: String) {
        sharedViewModel.getHourlyWeather()
        sharedViewModel.hourlyList.observe(viewLifecycleOwner, {

            recyclerAdapter = CustomRecyclerAdapter(requireContext(), it.list.filter { getSelectedData(it.dt) == data })
//            recyclerAdapter = CustomRecyclerAdapter(requireContext(), it.list)
            binding.recyclerView.apply {
                layoutManager =
                    LinearLayoutManager(
                        requireContext(),
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                adapter = recyclerAdapter
                setHasFixedSize(true)

            }
        })
    }
}
