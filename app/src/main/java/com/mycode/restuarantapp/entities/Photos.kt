package com.mycode.restuarantapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class Photos :Serializable {
    @SerializedName("height")
    private var height : Int = 0

    @SerializedName("html_attributions")
    private var html : List<String> = arrayListOf()

    @SerializedName("photo_reference")
    private lateinit var photoref : String

    @SerializedName("width")
    private var width : Int = 0

    fun getHeight() : Int {
        return height
    }

    fun setHeight(height : Int) {
         this.height=height
    }

    fun gethtml() : List<String> {
        return html
    }

    fun sethtml(html : List<String>) {
        this.html=html
    }

    fun getPhotoRef() : String {
        return photoref
    }

    fun setPhotoRef(photoref : String) {
        this.photoref=photoref
    }

    fun getWidth() : Int {
        return width
    }

    fun setWidth(width : Int) {
        this.width=width
    }

}