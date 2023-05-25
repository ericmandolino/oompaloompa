package com.oompa.loompa.model

data class OompaPage(
    val current: Int,
    val total: Int,
    val results: List<Oompa>
)
