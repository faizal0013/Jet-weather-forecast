package com.demo.jetweatherforecast.network

import com.demo.jetweatherforecast.models.Weather
import com.demo.jetweatherforecast.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query
import javax.inject.Singleton

@Singleton
interface WeatherAPI {

    @GET(value = "data/2.5/forecast")
    suspend fun getWeather(
        @Query("q") query: String,
        @Query("units") units: String = "imperial",
        @Query("appid") appId: String = Constants.API_KEY
    ) : Weather
}