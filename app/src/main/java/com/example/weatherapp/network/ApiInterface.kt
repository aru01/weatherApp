package com.example.weatherapp.network

import com.example.weatherapp.weather_info.data.model.WeatherClassResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
    @GET("weather")
    fun callApiForWeatherInfo(@Query("id") cityId: Int): Call<WeatherClassResponse>
}