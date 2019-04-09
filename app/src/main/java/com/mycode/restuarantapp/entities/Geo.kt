package com.mycode.restuarantapp.entities

import android.location.Location
import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Geo: Serializable {

    private var distance : Int =0
    @SerializedName("location")
    private lateinit var locate : Locator

    fun getLocation() : Locator{
        return locate
    }

    fun setLocation(locate: Locator){
        this.locate=locate
    }
    fun getDistance() :Int{
        return distance
    }

    fun setDistance(distance: Int ){
        this.distance=distance
    }
}