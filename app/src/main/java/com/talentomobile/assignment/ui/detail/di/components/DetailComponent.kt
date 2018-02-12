package com.talentomobile.assignment.ui.main.di.components

import com.talentomobile.assignment.di.components.AppComponent
import com.talentomobile.assignment.ui.detail.DetailActivity
import com.talentomobile.assignment.ui.main.di.modules.DetailActivityModule
import com.talentomobile.assignment.ui.main.di.scopes.DetailScope
import dagger.Component

/**
 * Created by pavel on 25/1/18.
 */
@DetailScope
@Component(dependencies = [AppComponent::class], modules = [DetailActivityModule::class])
interface DetailComponent{

    fun inject(detailActivity: DetailActivity)
}