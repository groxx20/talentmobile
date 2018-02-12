package com.talentomobile.assignment.di.components

import com.talentomobile.assignment.di.modules.NetworkModule
import com.talentomobile.assignment.di.scopes.PerService
import com.talentomobile.assignment.service.GeneralService
import dagger.Component

/**
 * Created by pavel on 24/1/18.
 */
@PerService
@Component(dependencies = [AppComponent::class], modules = [NetworkModule::class])
interface NetworkComponent{

    fun inject(generalService: GeneralService)
}