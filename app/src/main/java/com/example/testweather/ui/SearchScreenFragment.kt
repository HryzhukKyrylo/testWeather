package com.example.testweather.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.ui.viewmodel.SharedViewModel
import com.example.testweather.util.preference.PreferenceHelper
import kotlinx.android.synthetic.main.fragment_search_screen.*

class SearchScreenFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val geocoder by lazy { Geocoder(requireContext()) }
    private lateinit var prefs: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_search_screen, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        toolbar_home.setNavigationIcon(R.drawable.ic_back)
        toolbar_home.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        initSettings()
        initList()
        init()
    }

    private fun init() {
        prefs = PreferenceHelper.customPreference(requireContext(), Const.CUSTOM_PREF_NAME)
    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        sharedViewModel.city.observe(viewLifecycleOwner, {
            val city = it ?: "Default"
            twCity.text = "City = $city"
        })
        sharedViewModel.lat.observe(viewLifecycleOwner, {
            val lat = it ?: "Default"
            twLatitude.text = "Latitude = $lat"
        })
        sharedViewModel.lon.observe(viewLifecycleOwner, {
            val lon = it ?: "Default"
            twLongitude.text = "Longitude = $lon"
        })
    }

    private fun initList() {
        searchTextView.setOnQueryTextListener(object :
            androidx.appcompat.widget.SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    sharedViewModel.searchCity(query, geocoder, prefs)
                else
                    Toast.makeText(requireContext(), "City not found", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}


