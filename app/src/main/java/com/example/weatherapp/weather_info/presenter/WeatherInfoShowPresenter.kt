package com.example.weatherapp.weather_info.presenter

interface WeatherInfoShowPresenter {
    fun fetchCityList()
    fun fetchWeatherInfo(cityId: Int)
    fun detachView()
}