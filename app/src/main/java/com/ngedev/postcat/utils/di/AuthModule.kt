package com.ngedev.postcat.utils.di

import com.ngedev.postcat.data.repository.AuthRepositoryImpl
import com.ngedev.postcat.domain.repository.AuthRepository
import com.ngedev.postcat.domain.usecase.auth.AuthInteractor
import com.ngedev.postcat.domain.usecase.auth.AuthUseCase
import com.ngedev.postcat.ui.auth.AuthViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

object AuthModule {

    val authModule = module {
        factoryOf(::AuthInteractor) { bind<AuthUseCase>() }
        viewModelOf(::AuthViewModel)
    }

    val repositoryModule = module {
        factoryOf(::AuthRepositoryImpl) {
            bind<AuthRepository>()
        }
    }

}