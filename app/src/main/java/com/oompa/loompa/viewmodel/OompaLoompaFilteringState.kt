package com.oompa.loompa.viewmodel

data class OompaLoompaFilteringState(
    val selectedGenders: List<String>,
    val onGenderFilterChanged: (List<String>) -> Unit,
    val selectedProfessions: List<String>,
    val onProfessionFilterChanged: (List<String>) -> Unit,
)