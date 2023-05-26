package com.oompa.loompa.model

data class OompaLoompaPage(
    val current: Int,
    val total: Int,
    val results: List<OompaLoompa>
)
