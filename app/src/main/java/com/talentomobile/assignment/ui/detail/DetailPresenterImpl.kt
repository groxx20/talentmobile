package com.talentomobile.assignment.ui.detail

import android.util.Log
import com.google.android.gms.maps.model.LatLng
import com.talentomobile.assignment.dto.ResponseDtoWeather
import com.talentomobile.assignment.model.Weather
import com.talentomobile.assignment.network.data.NetworkError
import com.talentomobile.assignment.service.IGeneralService

/**
 * Created by pavel on 10/2/18.
 */

class DetailPresenterImpl(private val iGeneralService: IGeneralService,private val detailView: DetailView) : DetailPresenter, IGeneralService.OnGetInfoListener {


    private val TAG: String = "DetailPresenter"
    /**
     *  Request stations of searched place
     */
    override fun obtainData(south: Double, north:Double , east: Double, west: Double) {
        detailView.showLoading()
        iGeneralService.getInfo(south,north,east,west, this)
    }

    override fun onSuccess(weather: ResponseDtoWeather<Weather>) {

        Log.d(TAG, "success, got some stations to show")

        var temperatureAvg= 0
        var humidityAvg  = 0
        val coordinates : ArrayList<LatLng> = ArrayList()
        val names_stations : ArrayList<String> = ArrayList()

        for( weatherObj: Weather in weather.weatherObservations!!){

            temperatureAvg += weatherObj.temperature
            humidityAvg += weatherObj.humidity

            val latLng = LatLng(weatherObj.lat, weatherObj.lng)

            coordinates.add(latLng)
            names_stations.add(weatherObj.stationName)

        }

        temperatureAvg /= weather.weatherObservations!!.size
        humidityAvg /= weather.weatherObservations!!.size

        detailView.drawData(temperatureAvg , coordinates, names_stations)
        detailView.hideLoading()

    }

    override fun onFailure(networkError: NetworkError) {

        detailView.hideLoading()
        detailView.onFailure(networkError.appErrorMessage)
        Log.d(TAG, "failure, something went wrong or found 0 stations")
    }


}