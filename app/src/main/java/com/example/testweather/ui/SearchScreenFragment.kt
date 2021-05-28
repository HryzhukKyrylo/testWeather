package com.example.testweather.ui

import android.location.Geocoder
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.testweather.R
import com.example.testweather.ui.viewmodel.SharedViewModel
import kotlinx.android.synthetic.main.fragment_search_screen.*


class SearchScreenFragment : Fragment() {

    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val geocoder by lazy { Geocoder(requireContext()) }

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
        initList()

    }

    private fun initList() {

        searchTextView.maxWidth = Int.MAX_VALUE
        searchTextView.setOnQueryTextListener(object : androidx.appcompat.widget.SearchView.OnQueryTextListener{

            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("Not yet implemented")
            }


            override fun onQueryTextChange(newText: String?): Boolean {
                TODO("Not yet implemented")
            }

        })
        val searchView = searchTextView as androidx.appcompat.widget.SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(p0: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }

        })



    }


}


