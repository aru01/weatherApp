package com.example.weatherapp.weather_info.presenter

import android.view.View
import com.example.weatherapp.weather_info.data.model.WeatherInfoShowModel
import com.example.weatherapp.common.RequestListener
import com.example.weatherapp.weather_info.data.model.City
import com.example.weatherapp.weather_info.data.model.WeatherDataModel
import com.example.weatherapp.weather_info.data.model.WeatherClassResponse
import com.example.weatherapp.weather_info.view.MainActivityView
import com.example.weatherapp.utils.kelvinToCelsius
import com.example.weatherapp.utils.unixTimestampToDateTimeString
import com.example.weatherapp.utils.unixTimestampToTimeString

class WeatherInfoShowPresenterImpl(
        private var view: MainActivityView?,
        private val model: WeatherInfoShowModel) : WeatherInfoShowPresenter {

    override fun fetchCityList() {
        model.getCityList(object :
            RequestListener<MutableList<City>> {

            override fun onRequestSuccess(data: MutableList<City>) {
                view?.onCityListFetchSuccess(data)
            }


            override fun onRequestFailed(errorMessage: String) {
                view?.onCityListFetchFailure(errorMessage)
            }
        })
    }

    override fun fetchWeatherInfo(cityId: Int) {

        view?.handleProgressBarVisibility(View.VISIBLE)


        model.getWeatherInformation(cityId, object :
            RequestListener<WeatherClassResponse> {


            override fun onRequestSuccess(data: WeatherClassResponse) {

                view?.handleProgressBarVisibility(View.GONE) // let view know about progress bar visibility

                // data formatting to show on UI
                val weatherDataModel = WeatherDataModel(
                    dateTime = data.dt.unixTimestampToDateTimeString(),
                    temperature = data.main.temp.kelvinToCelsius().toString(),
                    cityAndCountry = "${data.name}, ${data.sys.country}",
                    weatherConditionIconUrl = "http://openweathermap.org/img/w/${data.weather[0].icon}.png",
                    weatherConditionIconDescription = data.weather[0].description,
                    humidity = "${data.main.humidity}%",
                    pressure = "${data.main.pressure} mBar",
                    visibility = "${data.visibility/1000.0} KM",
                    sunrise = data.sys.sunrise.unixTimestampToTimeString(),
                    sunset = data.sys.sunset.unixTimestampToTimeString()
                )

                view?.onWeatherInfoFetchSuccess(weatherDataModel)
            }

            override fun onRequestFailed(errorMessage: String) {
                view?.handleProgressBarVisibility(View.GONE)

                view?.onWeatherInfoFetchFailure(errorMessage)
            }
        })
    }

    override fun detachView() {
        view = null
    }
}