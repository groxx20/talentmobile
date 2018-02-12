package com.talentomobile.assignment.network

import com.talentomobile.assignment.dto.ResponseDto
import com.talentomobile.assignment.dto.ResponseDtoWeather
import com.talentomobile.assignment.model.Place
import com.talentomobile.assignment.model.Weather
import com.talentomobile.assignment.utils.Constants
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import rx.Observable

/**
 * Created by pavel on 23/1/18.
 */
interface NetworkService {


    @GET(Constants.API_SEARCH)
    fun getPlace(
            @Query("q") q: String,
    @Query("maxRows") maxRows: Int,
    @Query("startRow") startRow: Int,
    @Query("lang") lang: String,
    @Query("isNameRequired") isNameRequired: Boolean,
    @Query("style") style: String,
    @Query("username") username: String): Observable<ResponseDto<Place>>

    @GET(Constants.API_WEATHER)
    fun getInfo(
            @Query("north") north: Double,
            @Query("south") south: Double,
            @Query("east") east: Double,
            @Query("west") west: Double,
            @Query("username") username: String): Observable<ResponseDtoWeather<Weather>>

}

