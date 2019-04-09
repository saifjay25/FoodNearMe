package com.mycode.restuarantapp.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        private lateinit var retro : Retrofit
        fun getClient() : Retrofit{
           val intercept = HttpLoggingInterceptor()
            intercept.level = (HttpLoggingInterceptor.Level.BODY)
            val client : OkHttpClient = OkHttpClient.Builder().addInterceptor(intercept).build()
            retro = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
            return retro
        }
    }
}