package com.talentomobile.assignment.di.modules

import android.content.Context
import com.talentomobile.assignment.TalentoApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

/**
 * Created by pavel on 22/1/18.
 */

@Module
class AppModule (private val mApp: TalentoApp){

    @Singleton
    @Provides
    fun provideContext(): Context {
        return mApp
    }

    @Singleton
    @Provides
    fun provideApplication(): TalentoApp {
        return mApp
    }

}