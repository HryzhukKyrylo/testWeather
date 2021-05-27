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
        searchTextView.setOnSearchClickListener {
            val text = searchTextView.query
            Toast.makeText(requireContext(), text.toString(), Toast.LENGTH_SHORT).show()
//            sharedViewModel.searchCity(text.toString(),geocoder)
        }
//        searchTextView.setOnQueryTextListener(object :SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//        })
//        searchTextView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
//            override fun onQueryTextSubmit(query: String?): Boolean {
//                return true
//            }
//
//            override fun onQueryTextChange(newText: String?): Boolean {
//                return true
//            }
//
//        })

    }


}

private fun SearchView.setOnQueryTextListener(onQueryTextListener: SearchView.OnQueryTextListener) {

}

