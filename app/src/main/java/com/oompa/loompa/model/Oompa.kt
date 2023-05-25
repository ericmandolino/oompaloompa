package com.oompa.loompa.model

data class Oompa(
    val first_name: String,
    val last_name: String,
    val favorite: OompaFavorite,
    val gender: String,
    val image: String,
    val profession: String,
    val email: String,
    val age: Int,
    val country: String,
    val height: Int,
    val id: Long
)