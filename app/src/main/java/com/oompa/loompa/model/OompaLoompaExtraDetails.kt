package com.oompa.loompa.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaExtraDetails(
    val description: String,
    val quota: String,
    @SerializedName(value = "first_name") val firstName: String,
    @SerializedName(value = "last_name") val lastName: String,
    val favorite: OompaLoompaFavorite,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
)
