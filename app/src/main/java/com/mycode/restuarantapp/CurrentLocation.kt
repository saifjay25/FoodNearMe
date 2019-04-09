package com.mycode.restuarantapp

class CurrentLocation {
    companion object {
        var current : String = ""
        var latitude : Double =0.0
        var longitude : Double = 0.0
        var endlat : Double = 0.0
        var endlon : Double = 0.0
        var latlist : ArrayList<Double> = ArrayList()
        var longlist : ArrayList<Double> = ArrayList()
        var raddouble : Double = 0.0
        fun getlonglist() : ArrayList<Double>{
            return longlist
        }
        fun getlatlist() : ArrayList<Double>{
            return latlist
        }
    }
}