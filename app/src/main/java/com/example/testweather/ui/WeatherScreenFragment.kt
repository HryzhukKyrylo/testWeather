package com.example.testweather.ui

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import com.example.testweather.model.HeadRecyclerSection
import com.example.testweather.model.ParametersDayRecyclerSection
import com.example.testweather.ui.viewmodel.SharedViewModel
import com.example.testweather.util.IconHelper
import com.example.testweather.util.adapter.CustomRecyclerAdapter
import com.example.testweather.util.adapter.WeatherItem
import com.example.testweather.util.getDateDayString
import com.example.testweather.util.getSelectedData
import com.example.testweather.util.preference.PreferenceHelper
import com.example.testweather.util.preference.PreferenceHelper.screen
import com.example.testweather.util.preference.PreferenceHelper.units
import com.example.testweather.util.preference.PreferenceHelper.units_text
import com.example.testweather.util.preference.PreferenceHelper.windSpeed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen),
    CustomRecyclerAdapter.OnItemClickedListener {
    private lateinit var binding: FragmentWeatherScreenBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var recyclerAdapter: CustomRecyclerAdapter
    private lateinit var prefs: SharedPreferences
    override fun onStart() {
        super.onStart()
        prefs = PreferenceHelper.customPreference(requireContext(), CUSTOM_PREF_NAME)
        with(sharedViewModel) {
            setScreen(prefs.screen)
            initUnits(prefs.units.toString())
            initUnitsText(prefs.units_text.toString())
            initWindSpeed(getString(prefs.windSpeed))
        }
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherScreenBinding.inflate(inflater, container, false)
        initListeners()
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (checkConnectivity(requireContext())) {
            sharedViewModel.startScreen.observe(viewLifecycleOwner, {
                startScreen(it)
            })
        } else {
            Toast.makeText(requireContext(), getString(R.string.not_connection), Toast.LENGTH_SHORT)
                .show()
        }
    }

    fun checkConnectivity(context: Context): Boolean {

        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        } else {
            return false
        }
    }


    private fun startScreen(scr: Int) {
        when (scr) {
            Const.DAILY_SECTION -> {
                sharedViewModel.getDailyWeather()
                sharedViewModel.dayCardSection.observe(viewLifecycleOwner, {
                    initCardView(it.textCity, it.imageIcon, it.textClouds, it.textTemp)
                })
                sharedViewModel.parametersDaySection.observe(viewLifecycleOwner, {
                    recyclerAdapter =
                        CustomRecyclerAdapter(requireContext(), listOf<WeatherItem>(it))
                    startRecycler()
                })
            }
            Const.THREE_DAY_SECTION -> {
                sharedViewModel.getWeekWeather()
                sharedViewModel.dayCardSection.observe(viewLifecycleOwner, {
                    initCardView(it.textCity, it.imageIcon, it.textClouds, it.textTemp)
                })
                sharedViewModel.dailySection.observe(viewLifecycleOwner, {
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(), it)
                    recyclerAdapter.setListener(this@WeatherScreenFragment)
                    startRecycler()
                })
            }
            Const.WEEK_SECTION -> {
                sharedViewModel.getWeekWeather()
                sharedViewModel.dayCardSection.observe(viewLifecycleOwner, {
                    initCardView(it.textCity, it.imageIcon, it.textClouds, it.textTemp)
                })
                sharedViewModel.dailySection.observe(viewLifecycleOwner, {
                    recyclerAdapter = CustomRecyclerAdapter(requireContext(), it)
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


    private fun startRecycler() {
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
        textCity: String, imageIcon: String, textClouds: String, textTemp: String
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

    override fun onEntryClicked(data: Int) {
        sharedViewModel.getHourlyWeather(getSelectedData(data))
        sharedViewModel.hourlySection.observe(viewLifecycleOwner, { list ->
            val listRecycler = mutableListOf<WeatherItem>()
            val items = ParametersDayRecyclerSection(
                pressure = list.first().main.pressure.toString(),
                humidity = list.first().main.humidity.toString(),
                windSpeed = list.first().wind.speed.toString() + sharedViewModel.getWindSpeed(),
                clouds = list.first().clouds.all.toString()
            )
            listRecycler.add(HeadRecyclerSection(selectedDay = getDateDayString(data)))
            listRecycler.addAll(list)
            listRecycler.add(items)

            recyclerAdapter = CustomRecyclerAdapter(requireContext(), listRecycler)
            startRecycler()

        })

    }

}
