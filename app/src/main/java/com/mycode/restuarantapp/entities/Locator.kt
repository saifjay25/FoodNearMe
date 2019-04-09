package com.mycode.restuarantapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Locator: Serializable {
    @SerializedName("lat")
    private var lat: Double = 0.0

    @SerializedName("lng")
    private var lon: Double = 0.0

    fun getLatitude(): Double {
        return lat
    }

    fun setLatitude(lat: Double) {
        this.lat = lat
    }
    fun getLongitude(): Double {
        return lon
    }

    fun setLongitude(lon: Double) {
        this.lon = lon
    }
}