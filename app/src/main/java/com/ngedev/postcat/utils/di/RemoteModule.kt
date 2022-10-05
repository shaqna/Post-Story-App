package com.ngedev.postcat.utils.di

import com.ngedev.postcat.data.source.service.AuthRemote
import com.ngedev.postcat.data.source.service.StoryRemote
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object RemoteModule {
    val authRemote = module {
        factoryOf(::AuthRemote)
        factoryOf(::StoryRemote)
    }
}