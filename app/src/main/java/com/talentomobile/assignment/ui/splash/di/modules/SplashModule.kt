package com.talentomobile.assignment.ui.splash.di.modules

import com.talentomobile.assignment.ui.splash.SplashPresenterImpl
import com.talentomobile.assignment.ui.splash.SplashView
import com.talentomobile.assignment.ui.splash.di.scopes.SplashScope
import dagger.Module
import dagger.Provides


/**
 * Created by pavel on 5/2/18.
 */

@Module
class SplashModule(private val splashView: SplashView){


    @Provides
    @SplashScope
    fun providesView(): SplashView{
        return  splashView
    }


    @Provides
    @SplashScope
    fun providesSplashPresenter( splashView: SplashView) : SplashPresenterImpl {
        return SplashPresenterImpl(splashView)
    }

}