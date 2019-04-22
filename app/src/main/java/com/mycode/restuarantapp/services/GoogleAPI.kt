package com.mycode.restuarantapp.services

import com.mycode.restuarantapp.entities.PlacesResults
import io.reactivex.Observable
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//retrofit api of the webservice
//return a call object that encapsulates a single request and response
// call is used to execute the get request
interface GoogleAPI {
    //relative url
    @GET("place/nearbysearch/json")
    fun getNearby(
        @Query("location") location: String,
        @Query("radius") radius: Double,
        @Query("type") type: String,
        @Query("key") key: String
    ) : Observable<PlacesResults>
    // want to get results from the call
}