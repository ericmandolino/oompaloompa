package com.oompa.loompa.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import com.oompa.loompa.data.connectivity.ConnectivityChangeListener
import com.oompa.loompa.data.connectivity.ConnectivityMonitor


abstract class AutomaticRetryViewModel constructor(
    private val connectivityMonitor: ConnectivityMonitor,
): ConnectivityChangeListener, ViewModel() {
    private var shouldRetryAutomatically by mutableStateOf(false)

    init {
        connectivityMonitor.registerConnectivityChangeListener(this)
    }

    override fun onCleared() {
        super.onCleared()
        connectivityMonitor.unregisterConnectivityChangeListener(this)
    }

    fun getAutomaticRetryState() : AutomaticRetryState {
        return AutomaticRetryState(
            shouldRetryAutomatically,
            updateAutomaticRetry = { enabled: Boolean -> run {
                shouldRetryAutomatically = enabled
            }},
        )
    }

    override fun onNewDefaultNetwork() {
        shouldRetryAutomatically = true
    }
}