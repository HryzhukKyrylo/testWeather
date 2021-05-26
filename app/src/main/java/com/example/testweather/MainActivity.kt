package com.example.testweather

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private var backPressed = 0L
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    override fun onBackPressed() {
        if(backPressed + 2000 > System.currentTimeMillis())
            super.onBackPressed()
        else
            Toast.makeText(this, resources.getString(R.string.back_press), Toast.LENGTH_SHORT).show()
        backPressed = System.currentTimeMillis()
    }

}