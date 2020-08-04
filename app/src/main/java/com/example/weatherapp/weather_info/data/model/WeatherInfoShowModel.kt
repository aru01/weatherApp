package com.example.weatherapp.weather_info.data.model

import com.example.weatherapp.common.RequestListener

interface WeatherInfoShowModel {
    fun getCityList(callback: RequestListener<MutableList<City>>)
    fun getWeatherInformation(cityId: Int, callback: RequestListener<WeatherClassResponse>)
}