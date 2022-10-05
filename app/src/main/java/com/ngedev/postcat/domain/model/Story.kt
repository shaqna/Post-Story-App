package com.ngedev.postcat.domain.model

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "story")
data class Story(
    @PrimaryKey
    val id: String,

    val name: String,

    val description: String,

    val photoUrl: String,

    val createdAt: String,

    val lat: Double? = 0.0,

    val lon: Double? = 0.0
): Parcelable
