package com.pride.weatherapp.server

import com.pride.weatherapp.clases.Current
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiInterface {
        @GET("v1/current.json?key=bdbb753421a44e00a37171142222106&q=Kalush&aqi=no")
        fun getCurrent(): Call<Current>

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