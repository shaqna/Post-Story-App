package com.ngedev.postcat

import android.app.Application
import com.ngedev.postcat.utils.di.AuthModule.repositoryModule
import com.ngedev.postcat.utils.di.HomeModule.storyModule
import com.ngedev.postcat.utils.di.LocalModule.databaseModule
import com.ngedev.postcat.utils.di.NetworkModule.interceptorModule
import com.ngedev.postcat.utils.di.NetworkModule.prefsModule
import com.ngedev.postcat.utils.di.NetworkModule.retrofitModule
import com.ngedev.postcat.utils.di.RemoteModule.authRemote
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class MyApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidLogger(Level.ERROR)
            androidContext(this@MyApplication)
            modules(
                listOf(
                    interceptorModule,
                    prefsModule,
                    retrofitModule,
                    databaseModule,
                    repositoryModule,
                    authRemote,
                    storyModule
                )
            )
        }
    }
}