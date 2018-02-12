package com.talentomobile.assignment.service

import android.app.Service
import android.content.Intent
import android.os.Binder
import android.os.IBinder
import android.support.annotation.Nullable
import com.talentomobile.assignment.dbdata.DataBaseProvider
import com.talentomobile.assignment.dbdata.db.PlaceDB

/**
 * Created by pavel on 10/2/18.
 */

class DataBaseService : Service() {
    private val mBinder = LocalService()

    @Nullable
    override fun onBind(intent: Intent): IBinder {
        return mBinder
    }

    inner class LocalService : Binder() {
        fun getService(): DataBaseService {
            return this@DataBaseService
        }
    }

    fun getTeams(): List<PlaceDB>{
        return DataBaseProvider.getAllPlaces()
    }
}