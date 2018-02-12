package com.talentomobile.assignment.ui.detail

import com.google.android.gms.maps.model.LatLng

/**
 * Created by pavel on 10/2/18.
 */
interface DetailView {


    fun showLoading()

    fun hideLoading()

    fun onFailure(msg:String)

    fun drawData(temperature: Int, coordinates : ArrayList<LatLng>?, names_station: ArrayList<String>?)


}