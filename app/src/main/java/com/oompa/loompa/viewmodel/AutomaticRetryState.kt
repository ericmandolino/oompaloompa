package com.oompa.loompa.viewmodel

data class AutomaticRetryState (
    val shouldRetryAutomatically: Boolean,
    val updateAutomaticRetry: (enabled: Boolean) -> Unit,
)