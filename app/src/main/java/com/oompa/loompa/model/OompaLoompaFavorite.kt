package com.oompa.loompa.model

import androidx.room.ColumnInfo
import com.google.gson.annotations.SerializedName

data class OompaLoompaFavorite(
    val color: String,
    val food: String,
    @ColumnInfo(name = "random_string") @SerializedName(value = "random_string") val randomString: String,
    val song: String
)
