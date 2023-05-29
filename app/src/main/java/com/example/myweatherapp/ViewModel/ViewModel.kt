package com.example.myweatherapp.ViewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myweatherapp.Dataclass.WeatherResponse
import com.example.myweatherapp.WeatherRepository
import kotlinx.coroutines.launch

class WeatherViewModel : ViewModel() {
    private val weatherRepository = WeatherRepository()

    private val _weatherData = MutableLiveData<WeatherResponse>()
    val weatherData: LiveData<WeatherResponse> = _weatherData

    fun fetchWeatherData(location: String) {
        viewModelScope.launch {
            val weatherResponse = weatherRepository.getCurrentWeather(location)
            _weatherData.value = weatherResponse
        }
    }
}