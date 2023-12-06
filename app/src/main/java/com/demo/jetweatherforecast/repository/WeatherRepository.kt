package com.demo.jetweatherforecast.repository

import com.demo.jetweatherforecast.data.DataOrException
import com.demo.jetweatherforecast.models.Weather
import com.demo.jetweatherforecast.network.WeatherAPI
import javax.inject.Inject

class WeatherRepository @Inject constructor(
    private val api: WeatherAPI
){
    suspend fun getWeather(cityQuery: String, unit: String): DataOrException<Weather, Boolean, Exception> {
        val response = try {
            api.getWeather(query = cityQuery, units = unit)

        } catch (e: Exception) {
            return DataOrException(e = e)
        }

        return DataOrException(data = response)
    }
}