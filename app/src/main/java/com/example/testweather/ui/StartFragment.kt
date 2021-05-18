package com.example.testweather.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.testweather.R
import kotlinx.android.synthetic.main.fragment_start.*
import kotlinx.coroutines.*


class StartFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_start, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showLaounch()
        GlobalScope.launch {
            delay(TIME_OUT)
            withContext(Dispatchers.Main){
                moveToNext()
            }
        }
    }

    private fun moveToNext() {
        with(findNavController()) {
            popBackStack(R.id.startFragment, true)
            navigate(R.id.weatherScreenFragment)
        }
    }

    private fun showLaounch() {
        val media = "android.resource://${requireActivity().packageName}/${R.raw.loading}"
        Glide.with(requireContext())
            .load(media)
            .into(imageViewStart)
    }
    companion object{
        private const val TIME_OUT: Long = 2000
    }

}