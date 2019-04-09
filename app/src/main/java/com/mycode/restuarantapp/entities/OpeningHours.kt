package com.mycode.restuarantapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class OpeningHours:Serializable {
    @SerializedName ("open_now")
    private var open: Boolean? = null

    fun getOpenNow() : Boolean? {
        return open
    }

    fun setOpenNow(open:Boolean?){
        this.open=open
    }

}