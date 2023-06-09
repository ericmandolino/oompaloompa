package com.oompa.loompa.data.connectivity

import android.net.ConnectivityManager.NetworkCallback
import android.net.Network

class ConnectivityMonitorNetworkCallback : ConnectivityMonitor, NetworkCallback() {
    private var connectivityChangeListeners = mutableSetOf<ConnectivityChangeListener>()

    override fun onAvailable(network: Network) {
        connectivityChangeListeners.forEach { it.onNewDefaultNetwork() }
    }

    override fun registerConnectivityChangeListener(listener: ConnectivityChangeListener) {
        connectivityChangeListeners.add(listener)
    }

    override fun unregisterConnectivityChangeListener(listener: ConnectivityChangeListener) {
        connectivityChangeListeners.remove(listener)
    }
}