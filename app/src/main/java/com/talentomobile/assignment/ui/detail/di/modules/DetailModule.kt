package com.talentomobile.assignment.ui.main.di.modules

import com.talentomobile.assignment.service.GeneralService
import com.talentomobile.assignment.service.IGeneralService
import com.talentomobile.assignment.ui.detail.DetailPresenterImpl
import com.talentomobile.assignment.ui.detail.DetailView
import com.talentomobile.assignment.ui.main.di.scopes.DetailScope
import dagger.Module
import dagger.Provides

/**
 * Created by pavel on 25/1/18.
 */

@Module
class DetailActivityModule ( private val detailView: DetailView) {

    @Provides
    @DetailScope
    fun providesView(): DetailView {
        return  detailView
    }

    @Provides
    @DetailScope
    fun provideGeneralService(): IGeneralService {
        return GeneralService()
    }


    @Provides
    @DetailScope
    fun providesDetailPresenter(iGeneralService: IGeneralService, detailView: DetailView) : DetailPresenterImpl {
        return DetailPresenterImpl(iGeneralService, detailView)
    }
}
