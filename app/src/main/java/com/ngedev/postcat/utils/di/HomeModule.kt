package com.ngedev.postcat.utils.di

import com.ngedev.postcat.data.repository.StoryRepositoryImpl
import com.ngedev.postcat.domain.repository.StoryRepository
import com.ngedev.postcat.domain.usecase.story.StoryInteractor
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import com.ngedev.postcat.ui.home.HomeViewModel
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module
import org.koin.androidx.viewmodel.dsl.viewModelOf


object HomeModule {
    val homeModule = module {
        factoryOf(::StoryInteractor) { bind<StoryUseCase>() }
        viewModelOf(::HomeViewModel)
    }

    val storyModule = module {
        factoryOf(::StoryRepositoryImpl) {
            bind<StoryRepository>()
        }
    }
}