package com.developersbreach.composeactors.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import androidx.core.content.ContextCompat

class NetworkManager(
    context: Context
) {

    private val manager: ConnectivityManager? =
        ContextCompat.getSystemService(context, ConnectivityManager::class.java)

    fun checkForActiveNetwork(): Boolean {
        val activeNetwork: Network? = manager?.activeNetwork
        val capabilities: NetworkCapabilities? = manager?.getNetworkCapabilities(activeNetwork)
        return capabilities != null &&
            capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }
}