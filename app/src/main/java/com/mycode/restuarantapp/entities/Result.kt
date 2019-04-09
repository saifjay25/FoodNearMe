package com.mycode.restuarantapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Result: Serializable {

    @SerializedName("geometry")
    private lateinit var geometry :Geo

    @SerializedName("icon")
    private lateinit var icon: String

    @SerializedName("id")
    private lateinit var id: String

    @SerializedName("name")
    private lateinit var name: String

    @SerializedName("opening_hours")
    private var opening: OpeningHours? = null

    @SerializedName("photos")
    private var pics: List<Photos> = arrayListOf()

    @SerializedName("place_id")
    private lateinit var placeid: String

    @SerializedName("rating")
    private var rating: Double =0.0

    @SerializedName("scope")
    private lateinit var scope: String

    @SerializedName("types")
    private var types: List<String> = arrayListOf()

    @SerializedName("vicinity")
    private lateinit var vicinity: String

    @SerializedName("price_level")
    private var price: Int = 0

    fun getGeometry() : Geo  {
        return geometry
    }

    fun setGeometry(geometry : Geo){
        this.geometry=geometry
    }

    fun getIcon() : String  {
        return icon
    }

    fun setIcon(icon : String){
        this.icon=icon
    }

    fun getId() : String  {
        return id
    }

    fun setId(icon : String){
        this.id=id
    }

    fun getname() : String  {
        return name
    }

    fun setname(name : String){
        this.name=name
    }

    fun getOpening() : OpeningHours?  {
        return opening
    }

    fun setOpening(opening : OpeningHours){
        this.opening=opening
    }

    fun getPlace() : String  {
        return placeid
    }

    fun setPlace(placeid : String){
        this.placeid=placeid
    }

    fun getRating() : Double  {
        return rating
    }

    fun setRating(rating : Double){
        this.rating=rating
    }

    fun getTypes() : List<String>  {
        return types
    }

    fun setTypes(types : List<String>){
        this.types=types
    }

    fun getVicinity() : String  {
        return icon
    }

    fun setVicinity(vicinity : String){
        this.vicinity=vicinity
    }

    fun getPrice() : Int  {
        return price
    }

    fun setPrice(price : Int){
        this.price=price
    }
}