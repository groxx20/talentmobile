package com.talentomobile.assignment.model

/**
 * Created by pavel on 10/2/18.
 */

data class Place(val bbox: bbox,
                 val name:String,
                 val countryCode:String,
                 val population:Int,
                 val lat: Double,
                 val lng: Double
)
