package com.ngedev.postcat.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.ngedev.postcat.domain.model.Story

@Database(entities = [Story::class, RemoteKeys::class], version = 1, exportSchema = false)
abstract class LocalDatabase: RoomDatabase() {
    abstract fun remoteKeysDao(): RemoteKeysDao
    abstract fun storyDao(): StoryDao
}