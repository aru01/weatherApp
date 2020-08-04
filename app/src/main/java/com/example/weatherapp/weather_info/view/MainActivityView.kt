package com.example.weatherapp.weather_info.view

import com.example.weatherapp.weather_info.data.model.City
import com.example.weatherapp.weather_info.data.model.WeatherDataModel

interface MainActivityView {
    fun handleProgressBarVisibility(visibility: Int)
    fun onCityListFetchSuccess(cityList: MutableList<City>)
    fun onCityListFetchFailure(errorMessage: String)
    fun onWeatherInfoFetchSuccess(weatherDataModel: WeatherDataModel)
    fun onWeatherInfoFetchFailure(errorMessage: String)
}