package com.oompa.loompa.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaFavorite(
    val color: String,
    val food: String,
    @SerializedName(value = "random_string")
    val randomString: String,
    val song: String
)
