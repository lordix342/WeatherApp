package com.pride.weatherapp.server

import com.pride.weatherapp.clases.WeatherClass
import io.reactivex.rxjava3.core.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {
        @GET("v1/forecast.json?key=bdbb753421a44e00a37171142222106&days=5&aqi=no&alerts=no")
        fun getWeather(@Query("q")location: String, @Query("lang") language: String): Observable<WeatherClass>
}

object ApiInstance {
        private val httpLoggingInterceptor = HttpLoggingInterceptor()

        init {
                httpLoggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        }

        private val okHttpClient = OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .build()
        private val retrofit by lazy {
                Retrofit.Builder()
                        .client(okHttpClient)
                        .baseUrl("http://api.weatherapi.com/")
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                        .build()
        }
        val api: ApiInterface by lazy {
                retrofit.create(ApiInterface::class.java)
        }
}