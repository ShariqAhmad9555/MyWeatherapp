package com.example.myweatherapp.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.speech.RecognizerIntent
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.myweatherapp.ViewModel.WeatherViewModel
import com.example.myweatherapp.databinding.ActivityMainBinding
import java.util.*

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var activity: Activity
    private val REQUESTCODESPEECHINPUT = 100
    private lateinit var weatherViewModel: WeatherViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout and get the binding object
        activity = this
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        weatherViewModel = ViewModelProvider(this).get(WeatherViewModel::class.java)

        weatherViewModel.weatherData.observe(this) { weatherResponse ->
            // Update UI with weatherResponse data
            if (weatherResponse != null) {
                val intent = Intent(this, ViewWeather::class.java)
                val bundle = Bundle()
                bundle.putString("cityname", weatherResponse.name)
                bundle.putString("citytemp", "${weatherResponse.main.temp}")
                bundle.putString("humidity", " ${weatherResponse.main.humidity}")
                bundle.putString("description", weatherResponse.weather[0].description)
                intent.putExtras(bundle) // Attach the bundle to the intent
                startActivity(intent)
            }
        }
        // setContentView(R.layout.activity_main)
        binding.voiceinput.setOnClickListener {
            // Do some work here
            startVoiceInput(activity)
        }
    }

    // Function to start voice input
    fun startVoiceInput(activity: Activity) {
        val intent = Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH)
        intent.putExtra(
            RecognizerIntent.EXTRA_LANGUAGE_MODEL,
            RecognizerIntent.LANGUAGE_MODEL_FREE_FORM
        )
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault())
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speak something...")

        try {
            activity.startActivityForResult(intent, REQUESTCODESPEECHINPUT)
        } catch (e: Exception) {
            Toast.makeText(
                activity,
                "Sorry, your device doesn't support speech recognition.",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    // Handle voice input result
    fun handleVoiceInputResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUESTCODESPEECHINPUT && resultCode == Activity.RESULT_OK) {
            val result = data?.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS)
            val spokenText = result?.get(0)

            // Handle the spoken text as needed
            Toast.makeText(activity, "You said: $spokenText", Toast.LENGTH_SHORT).show()
            // Fetch weather data
            weatherViewModel.fetchWeatherData("$spokenText")
        }
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        handleVoiceInputResult(requestCode, resultCode, data)
    }
}