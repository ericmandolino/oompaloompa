package com.oompa.loompa.model

import androidx.room.ColumnInfo
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "oompa_loompas")
data class OompaLoompa(
    @PrimaryKey val id: Long,
    @ColumnInfo(name = "first_name") @SerializedName(value = "first_name") val firstName: String,
    @ColumnInfo(name = "last_name") @SerializedName(value = "last_name") val lastName: String,
    @Embedded val favorite: OompaLoompaFavorite,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
)