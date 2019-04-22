package com.mycode.restuarantapp.adapter

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.location.Location
import android.os.AsyncTask
import android.service.autofill.FieldClassification
import android.support.v7.widget.RecyclerView
import android.util.SparseBooleanArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import com.github.aakira.expandablelayout.*
import com.mycode.restuarantapp.CurrentLocation
import com.mycode.restuarantapp.R
import com.mycode.restuarantapp.entities.OpeningHours
import com.mycode.restuarantapp.entities.Result


class RecyclerAdapter(con: Context, result: List<Result>) :RecyclerView.Adapter<RecyclerAdapter.imageholder>() {
    private var cons:Context = con
    //the list to fill adapter
    private var res : List<Result> = result
    private lateinit var listener: OnItemClickListener
    private var expandState : SparseBooleanArray = SparseBooleanArray()
    init{
        for(i in res.indices){
            expandState.append(i,false)
        }
    }
    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): imageholder {
        val v: View = LayoutInflater.from(cons).inflate(R.layout.row_layout, p0, false)
        return imageholder(v)
    }

    override fun getItemCount(): Int {
        return res.size
    }

    override fun onBindViewHolder(p0: imageholder, p1: Int) {
        val res : Result = res[p1]
        //gets distance
        CurrentLocation.endlat = res.getGeometry().getLocation().getLatitude()
        CurrentLocation.endlon = res.getGeometry().getLocation().getLongitude()
        p0.setIsRecyclable(false)
        p0.expandable.setInRecyclerView(true)
        p0.expandable.isExpanded = expandState.get(p1)
        val layout  = object : ExpandableLayoutListenerAdapter(){

            override fun onPreOpen() {
                rotater(p0.drop, 0f, 180f).start()
                expandState.put(p1, true)
            }

            override fun onPreClose() {
                rotater(p0.drop, 180f, 0f).start()
                expandState.put(p1, false)
            }

        }
        p0.expandable.setListener(layout)
        if(expandState[p1]){
            p0.drop.rotation = 180f
        }else{
            p0.drop.rotation = 0f
        }
        p0.drop.setOnClickListener{
            p0.expandable.toggle()
        }
        if(res.getOpening()!=null) {
            val open= "Open Now"
            val close= "Closed"
            if(res.getOpening()!!.getOpenNow()==true){
                p0.open.text = open
            }else{
                p0.open.text = close
            }
        }
        val price= "Price is: "
        p0.title.text = res.getname()
        p0.price.text = price+res.getPrice().toString()
        p0.rating.text = res.getRating().toString()
        p0.distance.text = res.getGeometry().getDistance().toString()
    }


    private fun getDistance() : Float {
        val l1 = Location("")
        l1.longitude = CurrentLocation.longitude
        l1.latitude = CurrentLocation.latitude
        val l2 = Location("")
        l2.latitude = CurrentLocation.endlat
        l2.longitude = CurrentLocation.endlon
        val distance : Float = l1.distanceTo(l2)
        return distance
    }

    private fun rotater(drop: RelativeLayout, from: Float, end: Float): ObjectAnimator {
        val animate : ObjectAnimator = ObjectAnimator.ofFloat(drop,"rotation",from, end)
        animate.duration = 300
        animate.interpolator = Utils.createInterpolator(Utils.LINEAR_INTERPOLATOR)
        return animate

    }

    inner class imageholder(item: View) : RecyclerView.ViewHolder(item), View.OnClickListener {
        var title:TextView
        var open: TextView
        var rating: TextView
        var price: TextView
        var drop : RelativeLayout
        var distance : TextView
        var expandable : ExpandableLinearLayout
        init {
            title= item.findViewById(R.id.title)
            open = item.findViewById(R.id.open)
            rating = item.findViewById(R.id.rating)
            price= item.findViewById(R.id.price)
            drop= item.findViewById(R.id.drop)
            distance=item.findViewById(R.id.distance)
            expandable= item.findViewById(R.id.expand)
            item.setOnClickListener(this)
        }
        override fun onClick(p0: View?) {
            if(listener!=null){
                val position:Int = adapterPosition
                if(position!=RecyclerView.NO_POSITION){
                    listener.itemClick(position)
                }
            }
        }
    }

    interface OnItemClickListener{
        fun itemClick(position:Int) // regular clicks
    }

    //method is used to set activity as a listener for the interface
    fun setOnItemClickListener(listener: OnItemClickListener){
        this.listener= listener
    }
}
