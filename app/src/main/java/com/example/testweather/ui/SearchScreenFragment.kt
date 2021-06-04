package com.example.testweather.ui

import android.annotation.SuppressLint
import android.content.SharedPreferences
import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testweather.R
import com.example.testweather.const.Const
import com.example.testweather.databinding.FragmentSearchScreenBinding
import com.example.testweather.util.preference.PreferenceHelper
import com.example.testweather.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search_screen.*

class SearchScreenFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val geocoder by lazy { Geocoder(requireContext()) }
    private lateinit var prefs: SharedPreferences
    private lateinit var binding: FragmentSearchScreenBinding


    // View? = inflater.inflate(R.layout.fragment_search_screen, container, false)
    // how about like this?
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSearchScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initToolbar()
        initSettings()
        initListenerSearchView()
        initPreference()
    }

    private fun initToolbar() {
        binding.toolbarHome.setNavigationIcon(R.drawable.ic_white_back_button)
        binding.toolbarHome.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun initPreference() {
        prefs = PreferenceHelper.customPreference(requireContext(), Const.CUSTOM_PREF_NAME)
    }

    @SuppressLint("SetTextI18n")
    private fun initSettings() {
        sharedViewModel.city.observe(viewLifecycleOwner, {
            val city = it ?: resources.getString(R.string.settings_default)
            twCity.text = resources.getString(R.string.settings_city) + city
        })
        sharedViewModel.lat.observe(viewLifecycleOwner, {
            val lat = it ?: resources.getString(R.string.settings_default)
            twLatitude.text = resources.getString(R.string.settings_latitude) + lat
        })
        sharedViewModel.lon.observe(viewLifecycleOwner, {
            val lon = it ?: resources.getString(R.string.settings_default)
            twLongitude.text = resources.getString(R.string.settings_longitude) + lon
        })
    }

    private fun initListenerSearchView() {
        binding.searchTextView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null)
                    if (!sharedViewModel.searchCity(query, geocoder, prefs)) Toast.makeText(
                        requireContext(),
                        getString(R.string.city_not_found),
                        Toast.LENGTH_SHORT
                    ).show()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })
    }
}


