package com.oompa.loompa.viewmodel

data class OompaLoompaLongDetailState(
    val showLongDetail: Boolean,
    val longDetailName: String,
    val longDetailValue: String,
    val onShowLongDetail: (String, String) -> Unit,
    val onHideLongDetail: () -> Unit,
)