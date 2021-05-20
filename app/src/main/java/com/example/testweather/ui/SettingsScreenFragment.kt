package com.example.testweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.NavHostFragment
import com.example.testweather.R
import kotlinx.android.synthetic.main.fragment_settings_screen.*


class SettingsScreenFragment : Fragment() {

    private var argScreen: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_settings_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initListeners()
    }

    private fun initListeners() {
        btBack.setOnClickListener {
//            findNavController().popBackStack()
            val action = SettingsScreenFragmentDirections.navigateSettingsToWeatherScreen(argScreen)
            NavHostFragment.findNavController(this).navigate(action)
        }
        btSearch.setOnClickListener {
            NavHostFragment.findNavController(this).navigate(R.id.navigateSettingsToSearchScreen)
        }

        iwFahrenheit.setOnClickListener {
            iwCelsius.setImageResource(R.drawable.ic_celsius__1_)

            iwFahrenheit.setImageResource(R.drawable.ic_fahrenheit)
        }

        iwCelsius.setOnClickListener {
            iwFahrenheit.setImageResource(R.drawable.ic_fahrenheit__1_)

            iwCelsius.setImageResource(R.drawable.ic_celsius__1___1_)
        }

        twSelectCity.setOnClickListener {
            twSelectCity.setTextColor(resources.getColor(R.color.myBlue))
        }
        twDay.setOnClickListener {
            twThreeDay.setTextColor(resources.getColor(R.color.black))
            twWeek.setTextColor(resources.getColor(R.color.black))
            argScreen = 1
            twDay.setTextColor(resources.getColor(R.color.myBlue))
        }
        twThreeDay.setOnClickListener {
            twDay.setTextColor(resources.getColor(R.color.black))
            twWeek.setTextColor(resources.getColor(R.color.black))
            argScreen = 3
            twThreeDay.setTextColor(resources.getColor(R.color.myBlue))
        }
        twWeek.setOnClickListener {
            twDay.setTextColor(resources.getColor(R.color.black))
            twThreeDay.setTextColor(resources.getColor(R.color.black))
            argScreen = 7
            twWeek.setTextColor(resources.getColor(R.color.myBlue))
        }
        twM_s.setOnClickListener {
            twM_H.setTextColor(resources.getColor(R.color.black))
            twM_s.setTextColor(resources.getColor(R.color.myBlue))
        }
        twM_H.setOnClickListener {
            twM_s.setTextColor(resources.getColor(R.color.black))
            twM_H.setTextColor(resources.getColor(R.color.myBlue))
        }
    }

}