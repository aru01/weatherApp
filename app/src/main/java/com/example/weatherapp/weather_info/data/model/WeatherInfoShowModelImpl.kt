package com.example.weatherapp.weather_info.data.model

import android.content.Context
import com.google.gson.GsonBuilder
import com.example.weatherapp.common.RequestListener
import com.example.weatherapp.network.ApiInterface
import com.example.weatherapp.network.RetrofitClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.IOException
import com.google.gson.reflect.TypeToken

class WeatherInfoShowModelImpl(private val context: Context) :
    WeatherInfoShowModel {

    override fun getCityList(callback: RequestListener<MutableList<City>>) {

        try {
            val stream = context.assets.open("city_list.json")

            val size = stream.available()
            val buffer = ByteArray(size)
            stream.read(buffer)
            stream.close()
            val tContents  = String(buffer)

            val groupListType = object : TypeToken<ArrayList<City>>() {}.type
            val gson = GsonBuilder().create()
            val cityList: MutableList<City> = gson.fromJson(tContents, groupListType)

            callback.onRequestSuccess(cityList)

        } catch (e: IOException) {
           e.printStackTrace()
            callback.onRequestFailed(e.localizedMessage!!)
        }

    }
    override fun getWeatherInformation(cityId: Int, callback: RequestListener<WeatherClassResponse>) {

        val apiInterface: ApiInterface = RetrofitClient.client.create(ApiInterface::class.java)
        val call: Call<WeatherClassResponse> = apiInterface.callApiForWeatherInfo(cityId)

        call.enqueue(object : Callback<WeatherClassResponse> {
            override fun onResponse(call: Call<WeatherClassResponse>, response: Response<WeatherClassResponse>) {
                if (response.body() != null)
                    callback.onRequestSuccess(response.body()!!)
                else
                    callback.onRequestFailed(response.message())
            }
            override fun onFailure(call: Call<WeatherClassResponse>, t: Throwable) {
                callback.onRequestFailed(t.localizedMessage!!)
            }

        })
    }

}