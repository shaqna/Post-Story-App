package com.ngedev.postcat.utils.di

import androidx.room.Room
import com.ngedev.postcat.data.source.local.LocalDatabase
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

object LocalModule {

    val databaseModule = module {

        single {
            Room.databaseBuilder(androidContext(), LocalDatabase::class.java, "Local.db")
                .fallbackToDestructiveMigration().build()
        }

        factory {
            get<LocalDatabase>().remoteKeysDao()
        }

        factory {
            get<LocalDatabase>().storyDao()
        }
    }

}