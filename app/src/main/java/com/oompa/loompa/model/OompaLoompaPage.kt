package com.oompa.loompa.model

import com.google.gson.annotations.SerializedName

data class OompaLoompaPage(
    val current: Int,
    val total: Int,
    @SerializedName(value = "results") val oompaLoompas: List<OompaLoompa>,
)
