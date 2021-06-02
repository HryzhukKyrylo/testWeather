package com.example.testweather.ui

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testweather.R
import com.example.testweather.util.checkConnectivity
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.coroutines.*


class StartScreenFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLaunch()
        if (checkConnectivity(requireContext())) {
            GlobalScope.launch {
                delay(TIME_OUT)
                withContext(Dispatchers.Main) {
                    moveToNext()
                }
            }
        } else {
            Toast.makeText(requireContext(), getString(R.string.not_connection), Toast.LENGTH_SHORT)
                .show()
        }
    }


    private fun moveToNext() {
        with(findNavController()) {
            popBackStack(R.id.startFragment, true)
            navigate(R.id.weatherScreenFragment)
        }
    }

    private fun showLaunch() {
        val media = "android.resource://${requireActivity().packageName}/${R.raw.loading}"
        Glide.with(requireContext())
            .load(media)
            .into(imageViewStart)
    }
    companion object{
        private const val TIME_OUT: Long = 2000
    }
}