package com.example.testweather.ui.weather

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testweather.R
import com.example.testweather.adapter.CustomRecyclerAdapter
import com.example.testweather.adapter.WeatherItem
import com.example.testweather.databinding.FragmentWeatherRecyclerBinding
import com.example.testweather.util.checkConnectivity
import com.example.testweather.viewmodel.SharedViewModel


class WeatherRecyclerFragment : Fragment(), CustomRecyclerAdapter.OnItemClickedListener {
    private lateinit var binding: FragmentWeatherRecyclerBinding
    private lateinit var recyclerAdapter: CustomRecyclerAdapter
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private var tabListener: OnSelectionsListener? = null
    private var backPressed = 0L

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onAttachToParentFragment(parentFragment)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeatherRecyclerBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        if (checkConnectivity(requireContext())) {
            startFragment(requireArguments().getInt(START_SCREEN_ARGS))
        } else {
            Toast.makeText(requireContext(), getString(R.string.not_connection), Toast.LENGTH_SHORT)
                .show()
        }
    }

    private fun startFragment(someArgs: Int) =
        when (someArgs) {
            START_SCREEN -> {
                sharedViewModel.getWeather()
                sharedViewModel.listForRecycler.observe(viewLifecycleOwner, {
                    startRecycler(it)
                })
                onBackPressed()
            }
            else -> {
                sharedViewModel.getHourlyWeather(someArgs)
                sharedViewModel.listForRecycler.observe(viewLifecycleOwner, {
                    startRecycler(it)
                })
            }
        }

    private fun onBackPressed() {
        activity?.onBackPressedDispatcher?.addCallback(
            viewLifecycleOwner,
            object : OnBackPressedCallback(true) {
                override fun handleOnBackPressed() {
                    if (backPressed + 2000 > System.currentTimeMillis()) {
                        isEnabled = false
                        activity?.onBackPressed()
                    } else
                        Toast.makeText(
                            requireContext(),
                            resources.getString(R.string.back_press),
                            Toast.LENGTH_SHORT
                        ).show()
                    backPressed = System.currentTimeMillis()
                }
            })
    }

    private fun onAttachToParentFragment(fragment: Fragment?) {
        tabListener = fragment as? OnSelectionsListener
    }

    private fun startRecycler(list: List<WeatherItem>) {
        recyclerAdapter = CustomRecyclerAdapter(list)
        recyclerAdapter.setListener(this@WeatherRecyclerFragment)
        binding.recyclerView.layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
        binding.recyclerView.adapter = recyclerAdapter
        binding.recyclerView.setHasFixedSize(true)
    }

    override fun onEntryClicked(selectedDayUnixFormat: Int) {
        tabListener?.addSelectionsMarker(selectedDayUnixFormat)
    }

    companion object {
        private const val START_SCREEN = 0
        private const val START_SCREEN_ARGS = "screen_args"

    }
}