package com.oompa.loompa

import android.net.ConnectivityManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.oompa.loompa.data.connectivity.ConnectivityMonitorNetworkCallback
import com.oompa.loompa.ui.Navigation
import com.oompa.loompa.ui.theme.LoompaTheme
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var connectivityManager: ConnectivityManager
    @Inject lateinit var connectivityMonitorNetworkCallback: ConnectivityMonitorNetworkCallback

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        connectivityManager = getSystemService(ConnectivityManager::class.java)

        setContent {
            LoompaTheme {
                Navigation()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        connectivityManager.registerDefaultNetworkCallback(connectivityMonitorNetworkCallback)
    }

    override fun onPause() {
        super.onPause()
        connectivityManager.unregisterNetworkCallback(connectivityMonitorNetworkCallback)
    }
}