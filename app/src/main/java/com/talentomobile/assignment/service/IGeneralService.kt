package com.talentomobile.assignment.service

import com.talentomobile.assignment.dto.ResponseDto
import com.talentomobile.assignment.dto.ResponseDtoWeather
import com.talentomobile.assignment.model.Place
import com.talentomobile.assignment.model.Weather
import com.talentomobile.assignment.network.data.NetworkError
import okhttp3.ResponseBody

/**
 * Created by pavel on 24/1/18.
 */
interface IGeneralService {

    interface OnGetPlaceListener {
        fun onSuccess(place: ResponseDto<Place>)
        fun onFailure(networkError: NetworkError)
    }

    interface OnGetInfoListener {
        fun onSuccess(weather: ResponseDtoWeather<Weather>)
        fun onFailure(networkError: NetworkError)
    }

    fun getPlaces( name:String,listener: OnGetPlaceListener)

    fun getInfo(south: Double, north:Double , east: Double, west: Double,listener: OnGetInfoListener)

    fun cancelNetworkRequest()

}