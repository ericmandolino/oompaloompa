package com.oompa.loompa.model

data class OompaLoompa(
    val first_name: String,
    val last_name: String,
    val favorite: OompaLoompaFavorite,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val id: Long
)