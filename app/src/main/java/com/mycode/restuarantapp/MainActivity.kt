package com.mycode.restuarantapp

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.PorterDuff
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.webkit.PermissionRequest
import android.widget.*
import com.mycode.restuarantapp.CurrentLocation.Companion.endlat
import com.mycode.restuarantapp.CurrentLocation.Companion.endlon
import com.mycode.restuarantapp.adapter.RecyclerAdapter
import com.mycode.restuarantapp.entities.PlacesResults
import com.mycode.restuarantapp.entities.Result
import com.mycode.restuarantapp.services.APIClient
import com.mycode.restuarantapp.services.GoogleAPI
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.alert.view.*
import kotlinx.android.synthetic.main.row_layout.*
import retrofit2.Call
import retrofit2.Response
import java.util.*

class MainActivity : AppCompatActivity(),  RecyclerAdapter.OnItemClickListener {
    private lateinit var location : LocationManager
    private lateinit var recycl: RecyclerView
    private lateinit var adapter: RecyclerAdapter
    private lateinit var result : MutableList<Result>
    override fun onCreate(savedInstanceState: Bundle?)  {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        recycl =recycle
        recycl.setHasFixedSize(true)
        recycl.layoutManager = LinearLayoutManager(this)
        result = mutableListOf()
        showSorter()
        showData()
        googleApi()
        radius.setOnClickListener {
            CurrentLocation.getlatlist().clear()
            CurrentLocation.getlonglist().clear()
            val build :AlertDialog.Builder = AlertDialog.Builder(this)
            val layout: LayoutInflater = this.layoutInflater
            val view: View = layout.inflate(R.layout.alert, null)
            build.setTitle("Distance in miles: ")
            build.setView(view).setNegativeButton("cancel"){ dialogInterface: DialogInterface, i: Int ->
            }
            build.setView(view).setPositiveButton("create"){dialogInterface: DialogInterface, i: Int ->
                val get : TextView = view.findViewById(R.id.gettext)
                val getter: String = get.text.toString()
                CurrentLocation.raddouble = Integer.parseInt(getter) * 1609.344
                val type = spinner.selectedItem.toString()
                val api : GoogleAPI = APIClient.getClient().create(GoogleAPI::class.java)
                api.getNearby(CurrentLocation.current, CurrentLocation.raddouble, type,"AIzaSyBomoHX1r4nKmQb48FQHt4v0WGGc4hsay0").enqueue(
                    object : retrofit2.Callback<PlacesResults> {
                        override fun onResponse(call: Call<PlacesResults>, response: Response<PlacesResults>) {
                            if(response.isSuccessful){
                                result = response.body()!!.getResults()
                                for (i in result.indices){
                                    CurrentLocation.getlatlist().add(result[i].getGeometry().getLocation().getLatitude())
                                    CurrentLocation.getlonglist().add(result[i].getGeometry().getLocation().getLongitude())
                                    val dis=getDistance(result[i].getGeometry().getLocation().getLatitude(),result[i].getGeometry().getLocation().getLongitude())
                                    val miles : Int = (dis * 0.000621371192).toInt()+1
                                    result[i].getGeometry().setDistance(miles)
                                    if(result[i].getOpening()?.getOpenNow()==null){
                                        result.removeAt(i)
                                    }

                                }
                                adapter = RecyclerAdapter(applicationContext, result)
                                recycl.adapter = adapter
                                adapter.setOnItemClickListener(this@MainActivity)

                            }else{
                            }
                        }
                        override fun onFailure(call: Call<PlacesResults>, t: Throwable) {

                        }

                    }

                )
            }
            val alert = build.create()
            alert.show()
        }
        sorter.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP)
        sorter.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                if(position==1){
                    result.sortWith(Comparator { l1, l2 -> l1.getGeometry().getDistance().compareTo(l2.getGeometry().getDistance())})
                }
                if(position==2){
                    result.sortWith(Comparator { l1, l2 -> l1.getRating().compareTo(l2.getRating())})
                }
                if(position==3){
                    result.sortWith(Comparator { l1, l2 -> l1.getPrice().compareTo(l2.getPrice())})
                }
                adapter = RecyclerAdapter(applicationContext, result)
                recycl.adapter = adapter
                adapter.setOnItemClickListener(this@MainActivity)
            }

        }
        spinner.getBackground().setColorFilter(getResources().getColor(R.color.blue), PorterDuff.Mode.SRC_ATOP)
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                CurrentLocation.getlatlist().clear()
                CurrentLocation.getlonglist().clear()
                if(CurrentLocation.raddouble==0.0) {
                    CurrentLocation.raddouble = 1609.344
                }
                val type = spinner.selectedItem.toString()
                val api : GoogleAPI = APIClient.getClient().create(GoogleAPI::class.java)
                api.getNearby(CurrentLocation.current, CurrentLocation.raddouble, type,"AIzaSyBomoHX1r4nKmQb48FQHt4v0WGGc4hsay0").enqueue(
                    object : retrofit2.Callback<PlacesResults> {
                        override fun onResponse(call: Call<PlacesResults>, response: Response<PlacesResults>) {
                            if(response.isSuccessful){
                                result = response.body()!!.getResults()
                                for (i in result.indices){
                                    CurrentLocation.getlatlist().add(result[i].getGeometry().getLocation().getLatitude())
                                    CurrentLocation.getlonglist().add(result[i].getGeometry().getLocation().getLongitude())
                                    val dis=getDistance(result[i].getGeometry().getLocation().getLatitude(),result[i].getGeometry().getLocation().getLongitude())
                                    val miles : Int = (dis * 0.000621371192).toInt()+1
                                    result[i].getGeometry().setDistance(miles)
                                    if(result[i].getOpening()?.getOpenNow()==null){
                                        result.removeAt(i)
                                    }
                                }
                                adapter = RecyclerAdapter(applicationContext, result)
                                recycl.adapter = adapter
                                adapter.setOnItemClickListener(this@MainActivity)

                            }
                        }
                        override fun onFailure(call: Call<PlacesResults>, t: Throwable) {

                        }

                    }

                )
            }

        }
    }


    //this adaptes to wherever your location is
    private val listener : LocationListener = object: LocationListener{
        override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {

        }

        override fun onProviderEnabled(provider: String?) {

        }

        override fun onProviderDisabled(provider: String?) {

        }

        override fun onLocationChanged(location: Location?) {
            CurrentLocation.current = location!!.latitude.toString() + "," + location.longitude.toString() //current location
            CurrentLocation.latitude = location.latitude
            CurrentLocation.longitude = location.longitude
            CurrentLocation.getlatlist().clear()
            CurrentLocation.getlonglist().clear()
            if(CurrentLocation.raddouble==0.0) {
                CurrentLocation.raddouble = 1609.344
            }
            val type = spinner.selectedItem.toString()
            val api : GoogleAPI = APIClient.getClient().create(GoogleAPI::class.java)
            api.getNearby(CurrentLocation.current, CurrentLocation.raddouble, type,"AIzaSyBomoHX1r4nKmQb48FQHt4v0WGGc4hsay0").enqueue(
                object : retrofit2.Callback<PlacesResults> {
                    override fun onResponse(call: Call<PlacesResults>, response: Response<PlacesResults>) {
                        if(response.isSuccessful){
                            result = response.body()!!.getResults()
                            for (i in result.indices){
                                CurrentLocation.getlatlist().add(result[i].getGeometry().getLocation().getLatitude())
                                CurrentLocation.getlonglist().add(result[i].getGeometry().getLocation().getLongitude())
                                val dis=getDistance(result[i].getGeometry().getLocation().getLatitude(),result[i].getGeometry().getLocation().getLongitude())
                                val miles : Int = (dis * 0.000621371192).toInt()+1
                                result[i].getGeometry().setDistance(miles)
                                if(result[i].getOpening()?.getOpenNow() ==null){
                                    result.removeAt(i)
                                }
                            }
                            adapter = RecyclerAdapter(applicationContext, result)
                            recycl.adapter = adapter
                            adapter.setOnItemClickListener(this@MainActivity)
                        }else{
                        }
                    }
                    override fun onFailure(call: Call<PlacesResults>, t: Throwable) {
                    }

                }
            )
        }

    }
    //asks to get your current location
    private fun googleApi() {
        if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
        || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
            location = getSystemService(LOCATION_SERVICE) as LocationManager
            location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2*1000,1f, listener)
        }else{
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, Array(1) {android.Manifest.permission.ACCESS_FINE_LOCATION} , 12)
            }
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED){
                ActivityCompat.requestPermissions(this, Array(1){android.Manifest.permission.ACCESS_COARSE_LOCATION} , 13)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==12 || requestCode==13){
            if(ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED
                || ContextCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED){
                location = getSystemService(LOCATION_SERVICE) as LocationManager
                location.requestLocationUpdates(LocationManager.GPS_PROVIDER, 2*1000,1f, listener)
            }
        }
    }
    private fun showData(){
        val type : MutableList<String> = mutableListOf()
        type.add("restaurant")
        type.add("cafe")
        val adapter : ArrayAdapter<String> = ArrayAdapter(applicationContext,R.layout.spinner_item, type)
        spinner.adapter = adapter
    }
    private fun showSorter() {
        val tye : MutableList<String> = mutableListOf()
        tye.add("sort list")
        tye.add("distance")
        tye.add("rating")
        tye.add("pricing level")
        val adapter : ArrayAdapter<String> = ArrayAdapter(applicationContext,R.layout.spinner_item, tye)
        sorter.adapter = adapter
    }
    override fun itemClick(position: Int) {
        val builder = AlertDialog.Builder(this)
        builder.setMessage("Open Google Maps?")
            .setCancelable(true)
            .setPositiveButton("open") { dialog : DialogInterface, id :Int->
                val latitude =CurrentLocation.latlist[position]
                val longitude =CurrentLocation.longlist[position]
                val gmmIntentUri: Uri = Uri.parse("google.navigation:q=$latitude,$longitude")
                val mapIntent = Intent(Intent.ACTION_VIEW, gmmIntentUri)
                mapIntent.setPackage("com.google.android.apps.maps")
                try {
                    if (mapIntent.resolveActivity(packageManager) != null) {
                        startActivity(mapIntent)
                    }
                } catch (e: NullPointerException) {
                    Toast.makeText(applicationContext,"failure", Toast.LENGTH_SHORT).show()
                }
            }
            .setNegativeButton("No") { dialog, id -> dialog.cancel() }
        val alert = builder.create()
        alert.show()
    }
    private fun getDistance(endlat : Double, endlon : Double) : Float {
        val l1 = Location("")
        l1.longitude = CurrentLocation.longitude
        l1.latitude = CurrentLocation.latitude
        val l2 = Location("")
        l2.latitude = endlat
        l2.longitude = endlon
        val distance : Float = l1.distanceTo(l2)
        return distance
    }
}
