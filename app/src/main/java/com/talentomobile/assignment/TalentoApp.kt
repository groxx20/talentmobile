package com.talentomobile.assignment

import android.app.Application
import com.talentomobile.assignment.dbdata.DBManager
import com.talentomobile.assignment.di.components.AppComponent
import com.talentomobile.assignment.di.modules.AppModule
import com.talentomobile.assignment.di.components.DaggerAppComponent

/**
 * Created by pavel on 22/1/18.
 */


class TalentoApp : Application() {

    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()

        appComponent = DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .build()

        initDatabase()
    }

    /**
     * Init database
     */
    private fun initDatabase() {

        DBManager.init(this)
    }
}