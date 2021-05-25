package com.example.testweather.ui

import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.const.Const.CUSTOM_PREF_NAME
import com.example.testweather.ui.viewmodel.SharedViewModel
import com.example.testweather.util.preference.PreferenceHelper.celsiusSet
import com.example.testweather.util.preference.PreferenceHelper.citySet
import com.example.testweather.util.preference.PreferenceHelper.customPreference
import com.example.testweather.util.preference.PreferenceHelper.fahrenheitSet
import com.example.testweather.util.preference.PreferenceHelper.m_hSet
import com.example.testweather.util.preference.PreferenceHelper.m_sSet
import com.example.testweather.util.preference.PreferenceHelper.screen
import com.example.testweather.util.preference.PreferenceHelper.units
import kotlinx.android.synthetic.main.fragment_settings_screen.*


class SettingsScreenFragment : Fragment() {

    private lateinit var prefs: SharedPreferences
    private val sharedViewModel: SharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        prefs = customPreference(requireContext(), CUSTOM_PREF_NAME)
        return inflater.inflate(R.layout.fragment_settings_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initSettings()
        initListeners()
    }

    private fun initListeners() {
        btBack.setOnClickListener {
            findNavController().popBackStack()
        }
        btSearch.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.navigateSettingsToSearchScreen)
        }

        iwFahrenheit.setOnClickListener {
            iwCelsius.setImageResource(R.drawable.ic_celsius__1_)
            iwFahrenheit.setImageResource(R.drawable.ic_fahrenheit)
            prefs.units = Const.FAHRENHEIT_UNITS
            sharedViewModel.units = Const.FAHRENHEIT_UNITS
            // initSettings
            prefs.fahrenheitSet = true
            prefs.celsiusSet = false
        }
        iwCelsius.setOnClickListener {
            iwFahrenheit.setImageResource(R.drawable.ic_fahrenheit__1_)
            iwCelsius.setImageResource(R.drawable.ic_celsius__1___1_)
            prefs.units = Const.CELSIUS_UNITS
            sharedViewModel.units = Const.CELSIUS_UNITS
            prefs.celsiusSet = true
            prefs.fahrenheitSet = false
        }
        twSelectCity.setOnClickListener {
            twSelectCity.setTextColor(resources.getColor(R.color.myBlue))
            sharedViewModel.setSelectCity = "city"
            // initSettings
            prefs.citySet = true
        }
        twDay.setOnClickListener {
            twThreeDay.setTextColor(resources.getColor(R.color.black))
            twWeek.setTextColor(resources.getColor(R.color.black))
            twDay.setTextColor(resources.getColor(R.color.myBlue))
            sharedViewModel.setScreen(Const.DAILY_SECTION)
            // initSettings
            prefs.screen = Const.DAILY_SECTION
        }
        twThreeDay.setOnClickListener {
            twDay.setTextColor(resources.getColor(R.color.black))
            twWeek.setTextColor(resources.getColor(R.color.black))
            twThreeDay.setTextColor(resources.getColor(R.color.myBlue))
            sharedViewModel.setScreen(Const.THREE_DAY_SECTION)
            // initSettings
            prefs.screen = Const.THREE_DAY_SECTION

        }
        twWeek.setOnClickListener {
            twDay.setTextColor(resources.getColor(R.color.black))
            twThreeDay.setTextColor(resources.getColor(R.color.black))
            twWeek.setTextColor(resources.getColor(R.color.myBlue))
            sharedViewModel.setScreen(Const.WEEK_SECTION)
            // initSettings
            prefs.screen = Const.WEEK_SECTION

        }
        twM_s.setOnClickListener {
            twM_H.setTextColor(resources.getColor(R.color.black))
            twM_s.setTextColor(resources.getColor(R.color.myBlue))

            sharedViewModel.setMs = true
            sharedViewModel.setMh = false
            // initSettings
            prefs.m_sSet = true
            prefs.m_hSet = false

        }
        twM_H.setOnClickListener {
            twM_s.setTextColor(resources.getColor(R.color.black))
            twM_H.setTextColor(resources.getColor(R.color.myBlue))

            sharedViewModel.setMs = false
            sharedViewModel.setMh = true
            // initSettings
            prefs.m_sSet = false
            prefs.m_hSet = true

        }
    }

    private fun initSettings() {
        if (prefs.fahrenheitSet) iwFahrenheit.setImageResource(R.drawable.ic_fahrenheit)
        if (prefs.celsiusSet) iwCelsius.setImageResource(R.drawable.ic_celsius__1___1_)
        if (prefs.m_sSet) twM_s.setTextColor(resources.getColor(R.color.myBlue))
        if (prefs.m_hSet) twM_H.setTextColor(resources.getColor(R.color.myBlue))
        if (prefs.citySet) twSelectCity.setTextColor(resources.getColor(R.color.myBlue))
        when (prefs.screen) {
            Const.DAILY_SECTION -> twDay.setTextColor(resources.getColor(R.color.myBlue))
            Const.THREE_DAY_SECTION -> twThreeDay.setTextColor(resources.getColor(R.color.myBlue))
            Const.WEEK_SECTION -> twWeek.setTextColor(resources.getColor(R.color.myBlue))
        }

    }

}