package com.example.testweather.ui.weather

import android.content.Context
import android.content.SharedPreferences
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.fragment.NavHostFragment
import com.example.testweather.R
import com.example.testweather.const.Const.CUSTOM_PREF_NAME
import com.example.testweather.databinding.FragmentWeatherScreenBinding
import com.example.testweather.ui.viewmodel.SharedViewModel
import com.example.testweather.util.IconHelper
import com.example.testweather.util.preference.PreferenceHelper
import com.example.testweather.util.preference.PreferenceHelper.citySearch
import com.example.testweather.util.preference.PreferenceHelper.latSearch
import com.example.testweather.util.preference.PreferenceHelper.lonSearch
import com.example.testweather.util.preference.PreferenceHelper.m_hSet
import com.example.testweather.util.preference.PreferenceHelper.screen
import com.example.testweather.util.preference.PreferenceHelper.units
import com.example.testweather.util.preference.PreferenceHelper.units_text
import com.example.testweather.util.preference.PreferenceHelper.windSpeed
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class WeatherScreenFragment : Fragment(R.layout.fragment_weather_screen),
    OnSelectionsListener {
    private lateinit var binding: FragmentWeatherScreenBinding
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var prefs: SharedPreferences
    private var args_for_fragment = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        prefs = PreferenceHelper.customPreference(requireContext(), CUSTOM_PREF_NAME)
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
            initSettings()
            startScreen()
        } else {
            Toast.makeText(requireContext(), getString(R.string.not_connection), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun checkConnectivity(context: Context): Boolean {
        // www.не помню откуда взял
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo

        if (activeNetwork?.isConnected != null) {
            return activeNetwork.isConnected
        } else {
            return false
        }
    }

    private fun startScreen() {
        sharedViewModel.getDayItemWeather()
        sharedViewModel.dayCardSection.observe(viewLifecycleOwner, {
            initCardView(it.textCity, it.imageIcon, it.textClouds, it.textTemp)
        })

        val bundle = bundleOf("some_int" to 0)
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace<WeatherRecyclerFragment>(R.id.fragmentContainer, args = bundle)
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

    override fun addSelectionsMarker(data: Int) {
        args_for_fragment = data
        val bundle = bundleOf("some_int" to args_for_fragment)
        childFragmentManager.commit {
            setReorderingAllowed(true)
            replace<WeatherRecyclerFragment>(R.id.fragmentContainer, args = bundle)
            addToBackStack(null)
        }
    }

    private fun initSettings() {
        with(sharedViewModel) {
            setScreen(prefs.screen)
            initUnits(prefs.units.toString())
            initUnitsText(prefs.units_text.toString())
            initWindSpeed(getString(prefs.windSpeed))
            setMilInHour(prefs.m_hSet)

            prefs.citySearch?.let {
                if( it == "default"){
                    Toast.makeText(requireContext(), getText(R.string.select_city), Toast.LENGTH_SHORT).show()
                } else
                setCitySearch(it)
            }
            prefs.latSearch?.let { setLatSearch(it.toDouble()) }
            prefs.lonSearch?.let { setLonSearch(it.toDouble()) }
        }
    }
}
