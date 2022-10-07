package com.ngedev.postcat.utils.di

import com.ngedev.postcat.domain.usecase.story.StoryInteractor
import com.ngedev.postcat.domain.usecase.story.StoryUseCase
import com.ngedev.postcat.ui.post.PostStoryViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object PostStoryModule {
    val postStoryModule = module {
        factoryOf(::StoryInteractor) { bind<StoryUseCase>() }
        viewModelOf(::PostStoryViewModel)
    }
}