package com.mycode.restuarantapp.services

import io.reactivex.plugins.RxJavaPlugins
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class APIClient {

    companion object {
        private lateinit var retro : Retrofit
        fun getClient() : Retrofit{
            val intercept = HttpLoggingInterceptor()
            intercept.level = (HttpLoggingInterceptor.Level.BODY)
            val client : OkHttpClient = OkHttpClient.Builder().addInterceptor(intercept).build()
            //executes get request so u need an instance of retrofit
            retro = Retrofit.Builder()
                .baseUrl("https://maps.googleapis.com/maps/api/")
                    //this is how you define that you want to use gson to parse the response
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(client)
                .build()
            return retro
        }
    }
}