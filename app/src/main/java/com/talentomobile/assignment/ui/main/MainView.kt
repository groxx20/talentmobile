package com.talentomobile.assignment.ui.main

import com.talentomobile.assignment.dbdata.db.PlaceDB

/**
 * Created by pavel on 5/2/18.
 */
interface MainView {

    fun showLoading()

    fun hideLoading()

    fun showStuff(places: List<PlaceDB>)

    fun onFailure(msg:String)

    fun goNext(city:String)

}