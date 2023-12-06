package com.demo.jetweatherforecast.screens.main

import androidx.lifecycle.ViewModel
import com.demo.jetweatherforecast.data.DataOrException
import com.demo.jetweatherforecast.models.Weather
import com.demo.jetweatherforecast.repository.WeatherRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: WeatherRepository
) : ViewModel() {
    suspend fun getWeatherData(cityName: String, unit: String): DataOrException<Weather, Boolean, Exception> =
        repository.getWeather(cityQuery = cityName, unit = unit)
}