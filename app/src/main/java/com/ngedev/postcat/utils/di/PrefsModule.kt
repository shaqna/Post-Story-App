package com.ngedev.postcat.utils.di

import com.ngedev.postcat.external.AppSharedPreference
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

object PrefsModule {
    val appPrefsModule = module {
        singleOf(::AppSharedPreference)
    }
}