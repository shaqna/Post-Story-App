package com.ngedev.postcat.utils.di

import android.content.Context
import com.ngedev.postcat.data.source.service.ApiService
import com.ngedev.postcat.external.AuthInterceptor
import com.ngedev.postcat.external.AppSharedPreference
import com.ngedev.postcat.utils.Constants
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object NetworkModule {
    val prefsModule = module {
        single {
            androidApplication().getSharedPreferences(AppSharedPreference.SHARED_PREFS, Context.MODE_PRIVATE)
        }
    }

    val interceptorModule = module {
        singleOf(::AuthInterceptor)
    }

    val retrofitModule = module {
        single {
            val retrofit = Retrofit.Builder().baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .client(
                    OkHttpClient.Builder().addInterceptor(get<AuthInterceptor>()).build()
                )
                .build()

            retrofit.create(ApiService::class.java)
        }
    }
}