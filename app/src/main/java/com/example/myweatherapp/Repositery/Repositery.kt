package com.example.myweatherapp

import android.util.Log
import android.widget.Toast
import com.example.myweatherapp.Dataclass.WeatherResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherRepository {
    val API_KEY: String = "06c921750b9a82d8f5d1294e1586276f" // Use API key
    val retrofit = Retrofit.Builder()
        .baseUrl("https://api.openweathermap.org/data/2.5/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
    private val weatherService: WeatherService = retrofit.create(WeatherService::class.java)

    suspend fun getCurrentWeather(location: String): WeatherResponse? {
        val response = weatherService.getCurrentWeather(location, "$API_KEY")
        if (response.isSuccessful) {
            Log.d("WeatherResponse :", "${response.body()}")

            return response.body()
        }
        return null
    }
}







