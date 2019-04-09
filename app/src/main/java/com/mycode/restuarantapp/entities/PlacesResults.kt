package com.mycode.restuarantapp.entities

import com.google.gson.annotations.SerializedName
import java.io.Serializable

class PlacesResults : Serializable {

    @SerializedName("html_attributions")
    private var html : MutableList<Any> = mutableListOf()

    @SerializedName("next_page_token")
    private var nextPage : String = ""

    @SerializedName("results")
    private var results : MutableList<Result> = mutableListOf()

    @SerializedName("status")
    private var status : String = ""

    fun getHTML() : MutableList<Any> {
        return html
    }

    fun setHTML(html : MutableList<Any>) {
        this.html = html
    }

    fun getnextPage() : String {
        return nextPage
    }

    fun setnextPage(html : String) {
        this.nextPage = nextPage
    }

    fun getResults() : MutableList<Result> {
        return results
    }

    fun setResults(results : MutableList<Result>) {
        this.results = results
    }

}