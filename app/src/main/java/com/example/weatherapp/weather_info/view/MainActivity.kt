package com.example.weatherapp.weather_info.view

import androidx.appcompat.app.AppCompatActivity

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.weatherapp.R
import com.example.weatherapp.utils.convertToListOfCityName
import com.example.weatherapp.weather_info.data.model.WeatherInfoShowModel
import com.example.weatherapp.weather_info.data.model.WeatherInfoShowModelImpl
import com.example.weatherapp.weather_info.data.model.City
import com.example.weatherapp.weather_info.data.model.WeatherDataModel
import com.example.weatherapp.weather_info.presenter.WeatherInfoShowPresenter
import com.example.weatherapp.weather_info.presenter.WeatherInfoShowPresenterImpl
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.layout_input_city.*
import kotlinx.android.synthetic.main.layout_suntime.*
import kotlinx.android.synthetic.main.general.*
import kotlinx.android.synthetic.main.layout_detail_weather.*

class MainActivity : AppCompatActivity(), MainActivityView {

    private lateinit var model: WeatherInfoShowModel
    private lateinit var presenter: WeatherInfoShowPresenter

    private var cityList: MutableList<City> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        model = WeatherInfoShowModelImpl(applicationContext)
        presenter = WeatherInfoShowPresenterImpl(this, model)
        presenter.fetchCityList()
        btn_view_weather.setOnClickListener {
            output_group.visibility = View.GONE
            val spinnerSelectedItemPos = spinner.selectedItemPosition
            presenter.fetchWeatherInfo(cityList[spinnerSelectedItemPos].id)
        }
    }
    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }

    override fun handleProgressBarVisibility(visibility: Int) {
        progressBar?.visibility = visibility
    }
    override fun onCityListFetchSuccess(cityList: MutableList<City>) {
        this.cityList = cityList
        val arrayAdapter = ArrayAdapter(
            this,
            R.layout.support_simple_spinner_dropdown_item,
            cityList.convertToListOfCityName()
        )
        spinner.adapter = arrayAdapter
    }
    override fun onCityListFetchFailure(errorMessage: String) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_LONG).show()
    }

    override fun onWeatherInfoFetchSuccess(weatherDataModel: WeatherDataModel) {
        output_group.visibility = View.VISIBLE
        tv_error_message.visibility = View.GONE
        date_time?.text = weatherDataModel.dateTime
        temperature?.text = weatherDataModel.temperature
        tv_city_country?.text = weatherDataModel.cityAndCountry
        Glide.with(this).load(weatherDataModel.weatherConditionIconUrl).into(iv_weather_condition)
        tv_weather_condition?.text = weatherDataModel.weatherConditionIconDescription
        tv_humidity_value?.text = weatherDataModel.humidity
        tv_pressure_value?.text = weatherDataModel.pressure
        tv_visibility_value?.text = weatherDataModel.visibility
        tv_sunrise_time?.text = weatherDataModel.sunrise
        tv_sunset_time?.text = weatherDataModel.sunset
    }
    override fun onWeatherInfoFetchFailure(errorMessage: String) {
        output_group.visibility = View.GONE
        tv_error_message.visibility = View.VISIBLE
        tv_error_message.text = errorMessage
    }
}