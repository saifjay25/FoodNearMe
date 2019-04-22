package com.mycode.restuarantapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Result: Serializable {

    @SerializedName("geometry")
    private lateinit var geometry: Geo

    @SerializedName("name")
    private lateinit var name: String

    @SerializedName("opening_hours")
    private var opening: OpeningHours? =null

    @SerializedName("rating")
    private var rating: Double = 0.0

    @SerializedName("price_level")
    private var price: Int = 0

    fun getGeometry(): Geo {
        return geometry
    }


    fun getname(): String {
        return name
    }
    fun getOpening(): OpeningHours? {
        return opening
    }

    fun getRating(): Double {
        return rating
    }

    fun getPrice(): Int {
        return price
    }

}