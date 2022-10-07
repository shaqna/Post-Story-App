package com.ngedev.postcat.utils.di

import com.ngedev.postcat.domain.usecase.maps.MapsInteractor
import com.ngedev.postcat.domain.usecase.maps.MapsUseCase
import com.ngedev.postcat.ui.maps.MapsViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object MapsModule {
    val mapsModule = module {
        factoryOf(::MapsInteractor) {
            bind<MapsUseCase>()
        }
        viewModelOf(::MapsViewModel)
    }
}