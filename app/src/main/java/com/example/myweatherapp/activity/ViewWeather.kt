package com.example.myweatherapp.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.example.myweatherapp.databinding.ActivityViewWeatherBinding

class ViewWeather : AppCompatActivity() {
    private lateinit var viewWeatherBinding: ActivityViewWeatherBinding

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        viewWeatherBinding = ActivityViewWeatherBinding.inflate(layoutInflater)
        val view = viewWeatherBinding.root
        setContentView(view)
        val bundle = intent.extras
        val cityname = bundle!!.getString("cityname")
        val citytemp = bundle.getString("citytemp")
        val humidity = bundle.getString("humidity")
        val description = bundle.getString("description")
        Log.d("Viewwaether :", "$cityname /n" + "$citytemp /n" + "$humidity /n" + "$description /n")
        viewWeatherBinding.city.text = cityname
        viewWeatherBinding.temp.text = "$citytemp°F"
        viewWeatherBinding.humidity.text = "Humidity :$humidity"
        viewWeatherBinding.desc.text = description
    }
}