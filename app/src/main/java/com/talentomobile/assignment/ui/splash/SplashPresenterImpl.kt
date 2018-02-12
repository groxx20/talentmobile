package com.talentomobile.assignment.ui.splash

/**
 * Created by pavel on 5/2/18.
 */

class SplashPresenterImpl(private val splashView: SplashView) : SplashPresenter {


    override fun startProcess() {
        splashView.goNext()
    }

    override fun stopProcess() {
        splashView.hideLoading()
    }


}
