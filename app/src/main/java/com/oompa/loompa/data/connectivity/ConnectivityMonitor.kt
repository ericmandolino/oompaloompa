package com.oompa.loompa.data.connectivity

interface ConnectivityMonitor {
    fun registerConnectivityChangeListener(listener: ConnectivityChangeListener)

    fun unregisterConnectivityChangeListener(listener: ConnectivityChangeListener)
}