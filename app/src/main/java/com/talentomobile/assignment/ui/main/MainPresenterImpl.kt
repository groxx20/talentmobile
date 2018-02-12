package com.talentomobile.assignment.ui.main

import android.util.Log
import com.talentomobile.assignment.dbdata.DataBaseProvider
import com.talentomobile.assignment.dbdata.db.PlaceDB
import com.talentomobile.assignment.dto.ResponseDto
import com.talentomobile.assignment.model.Place
import com.talentomobile.assignment.network.data.NetworkError
import com.talentomobile.assignment.service.IGeneralService

/**
 * Created by pavel on 24/1/18.
 */

class MainPresenterImpl(private val iGeneralService: IGeneralService, private val mainView: MainView) : IGeneralService.OnGetPlaceListener, MainPresenter{

    private val TAG: String = "MainPresenter"

    /**
     *  Request of City from User
     */
    override fun getCity(name:String) {
        mainView.showLoading()
        iGeneralService.getPlaces(name, this)
    }

    override fun onSuccess(place: ResponseDto<Place>) {

        Log.d(TAG, "success, got the place")
        if(place.geonames != null && place.geonames!!.isNotEmpty()) {
            convertType(place.geonames!![0])
        }else{
            mainView.onFailure("nothing found")
        }
        mainView.hideLoading()


    }

    override fun onFailure(networkError: NetworkError) {
        Log.d(TAG, "failure, something went wrong")
        mainView.onFailure(networkError.appErrorMessage)
        mainView.hideLoading()
    }


    /**
     *  Convert response to fill DB
     */
    private fun convertType(place: Place){


        if (!DataBaseProvider.searchPlace(place.name)){
            val placeDB = PlaceDB(null,place.name,place.bbox.east,place.bbox.south,place.bbox.north,place.bbox.west,place.lat,place.lng,place.countryCode,place.population )
            DataBaseProvider.insertPlace(placeDB)
        }

        mainView.goNext(place.name)

    }


}