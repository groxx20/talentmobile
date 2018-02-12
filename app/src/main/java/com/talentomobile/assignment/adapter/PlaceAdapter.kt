package com.talentomobile.assignment.adapter

import android.content.Context
import android.support.v7.widget.CardView
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.talentomobile.assignment.R
import com.talentomobile.assignment.dbdata.db.PlaceDB
import com.talentomobile.assignment.ui.main.MainView

/**
 * Created by pavel on 10/2/18.
 */

class PlaceAdapter(private val placeList: List<PlaceDB>, private val context: Context, private val listener: MainView): RecyclerView.Adapter<PlaceAdapter.ViewHolder>() {

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        holder?.txtName?.text = placeList[position].name

        val population = context.resources.getString(R.string.population) + " " + placeList[position].population.toString()
        holder?.txtCountry?.text = population
        holder?.txtNational?.text = placeList[position].countryCode

        holder?.card?.setOnClickListener{ listener.goNext(placeList[position].name) }

    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent?.context).inflate(R.layout.place_layout, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return placeList.size
    }

    class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val txtName = itemView.findViewById<TextView>(R.id.name)!!
        val txtCountry = itemView.findViewById<TextView>(R.id.population)!!
        val txtNational = itemView.findViewById<TextView>(R.id.national)!!
        val card = itemView.findViewById<CardView>(R.id.card)!!

    }

}