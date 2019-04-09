package com.mycode.restuarantapp.services

import com.mycode.restuarantapp.entities.PlacesResults
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GoogleAPI {

    @GET("place/nearbysearch/json")
    fun getNearby(
        @Query("location") location: String,
        @Query("radius") radius: Double,
        @Query("type") type: String,
        @Query("key") key: String
    ) : Call<PlacesResults>
    // want to get results from the call
}