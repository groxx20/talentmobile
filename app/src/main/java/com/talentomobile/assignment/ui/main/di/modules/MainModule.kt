package com.talentomobile.assignment.ui.main.di.modules

import com.talentomobile.assignment.service.GeneralService
import com.talentomobile.assignment.service.IGeneralService
import com.talentomobile.assignment.ui.main.MainPresenterImpl
import com.talentomobile.assignment.ui.main.MainView
import com.talentomobile.assignment.ui.main.di.scopes.MainScope
import dagger.Module
import dagger.Provides

/**
 * Created by pavel on 25/1/18.
 */

@Module
class MainModule ( private val mainView: MainView) {

    @Provides
    @MainScope
    fun providesView(): MainView {
        return  mainView
    }

    @Provides
    @MainScope
    fun provideGeneralService(): IGeneralService {
        return GeneralService()
    }


    @Provides
    @MainScope
    fun providesMainPresenter(iGeneralService: IGeneralService, mainView: MainView) : MainPresenterImpl{
        return MainPresenterImpl(iGeneralService, mainView)
    }
}
