package com.codewithhiren.foodorderapplatest.utils


import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Inject

class NetworkStatusCheck @Inject constructor(@ApplicationContext context: Context) {

    var isNetworkAvailable = MutableStateFlow(false)
        private set

    private val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager


    private val networkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            isNetworkAvailable.value = true
        }

        override fun onLost(network: Network) {
            isNetworkAvailable.value = false
        }
    }

    init {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun unregisterNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }
}