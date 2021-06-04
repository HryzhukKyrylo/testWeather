package com.example.testweather.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.const.Const.CUSTOM_PREF_NAME
import com.example.testweather.databinding.FragmentSettingsScreenBinding
import com.example.testweather.util.preference.PreferenceHelper.checkCelsius
import com.example.testweather.util.preference.PreferenceHelper.checkCity
import com.example.testweather.util.preference.PreferenceHelper.checkFahrenheit
import com.example.testweather.util.preference.PreferenceHelper.checkMilesInHour
import com.example.testweather.util.preference.PreferenceHelper.checkMilesInSeconds
import com.example.testweather.util.preference.PreferenceHelper.customPreference
import com.example.testweather.util.preference.PreferenceHelper.screen
import com.example.testweather.util.preference.PreferenceHelper.units
import com.example.testweather.util.preference.PreferenceHelper.units_text
import com.example.testweather.util.preference.PreferenceHelper.windSpeed
import com.example.testweather.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_settings_screen.*


class SettingsScreenFragment : Fragment() {

    private lateinit var preferences: SharedPreferences
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private lateinit var binding: FragmentSettingsScreenBinding


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSettingsScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initPreference()
        initSettings()
        initListeners()
    }

    private fun initPreference() {
        preferences = customPreference(requireContext(), CUSTOM_PREF_NAME)
    }

    private fun initListeners() {
        buttonListener()
        imagesListener()
    }

    private fun buttonListener() {
        btBack.setOnClickListener {
            findNavController().popBackStack()
        }
        btSearch.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.navigateSettingsToSearchScreen)
        }
    }

    private fun imagesListener() {
        iwFahrenheit.setOnClickListener {
            binding.iwCelsius.setImageResource(R.drawable.ic_nonselected_celsius)
            binding.iwFahrenheit.setImageResource(R.drawable.ic_selected_fahrenheit)
            // initSettings
            preferences.units = Const.FAHRENHEIT_UNITS
            sharedViewModel.initUnits(Const.FAHRENHEIT_UNITS)
            preferences.checkFahrenheit = true
            preferences.checkCelsius = false
            preferences.units_text = Const.FAHRENHEIT_PREF
        }
        iwCelsius.setOnClickListener {
            iwFahrenheit.setImageResource(R.drawable.ic_nonselected_fahrenheit)
            iwCelsius.setImageResource(R.drawable.ic_selected_celsius)
            // initSettings
            preferences.units = Const.CELSIUS_UNITS
            sharedViewModel.initUnits(Const.CELSIUS_UNITS)
            preferences.checkCelsius = true
            preferences.checkFahrenheit = false
            preferences.units_text = Const.CELSIUS_PREF
        }
        twSelectCity.setOnClickListener {
            twSelectCity.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
            // initSettings
            preferences.checkCity = true
            NavHostFragment.findNavController(this).navigate(R.id.navigateSettingsToSearchScreen)
        }
        twDay.setOnClickListener {
            twThreeDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twWeek.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
            sharedViewModel.setScreen(Const.DAILY_SECTION)
            // initSettings
            preferences.screen = Const.DAILY_SECTION
        }
        twThreeDay.setOnClickListener {
            twDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twWeek.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twThreeDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
            sharedViewModel.setScreen(Const.THREE_DAY_SECTION)
            // initSettings
            preferences.screen = Const.THREE_DAY_SECTION

        }
        twWeek.setOnClickListener {
            twDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twThreeDay.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twWeek.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
            sharedViewModel.setScreen(Const.WEEK_SECTION)
            // initSettings
            preferences.screen = Const.WEEK_SECTION

        }
        twM_s.setOnClickListener {
            twM_H.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twM_s.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
            // initSettings
            preferences.windSpeed = R.string.m_s
            preferences.checkMilesInSeconds = true
            preferences.checkMilesInHour = false

        }
        twM_H.setOnClickListener {
            twM_s.setTextColor(ContextCompat.getColor(requireContext(), R.color.color_black))
            twM_H.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
            // initSettings
            preferences.windSpeed = R.string.mil_h
            preferences.checkMilesInSeconds = false
            preferences.checkMilesInHour = true

        }
    }

    private fun initSettings() {
        if (preferences.checkFahrenheit) iwFahrenheit.setImageResource(R.drawable.ic_selected_fahrenheit)
        if (preferences.checkCelsius) iwCelsius.setImageResource(R.drawable.ic_selected_celsius)
        if (preferences.checkMilesInSeconds) twM_s.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.myBlue
            )
        )
        if (preferences.checkMilesInHour) twM_H.setTextColor(
            ContextCompat.getColor(
                requireContext(),
                R.color.myBlue
            )
        )
        if (preferences.checkCity) {
            sharedViewModel.city.observe(viewLifecycleOwner, {
                twSelectCity.text = it
            })
            twSelectCity.setTextColor(ContextCompat.getColor(requireContext(), R.color.myBlue))
        }
        when (preferences.screen) {
            Const.DAILY_SECTION -> twDay.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.myBlue
                )
            )
            Const.THREE_DAY_SECTION -> twThreeDay.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.myBlue
                )
            )
            Const.WEEK_SECTION -> twWeek.setTextColor(
                ContextCompat.getColor(
                    requireContext(),
                    R.color.myBlue
                )
            )
        }
    }
}