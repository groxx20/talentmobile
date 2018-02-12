package com.talentomobile.assignment.model

/**
 * Created by pavel on 10/2/18.
 */
data class Weather(
                 val temperature:Int,
                 val humidity:Int,
                 val weatherCondition:String,
                 val lat: Double,
                 val lng: Double,
                 val stationName: String
)