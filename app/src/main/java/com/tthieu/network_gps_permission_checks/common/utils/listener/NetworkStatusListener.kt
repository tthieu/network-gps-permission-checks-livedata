package com.tthieu.network_gps_permission_checks.common.utils.listener

import android.annotation.TargetApi
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Context.CONNECTIVITY_SERVICE
import android.content.Intent
import android.content.IntentFilter
import android.net.*
import android.os.Build
import androidx.lifecycle.LiveData
import com.tthieu.network_gps_permission_checks.R

class NetworkStatusListener(val context: Context):
    LiveData<NetworkStatus>() {

    private var connectivityManager: ConnectivityManager = context.getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager

    private lateinit var connectivityManagerCallback: ConnectivityManager.NetworkCallback

    private val networkRequestBuilder: NetworkRequest.Builder = NetworkRequest.Builder()
        .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
        .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)

    override fun onActive() {
        super.onActive()
        updateConnection()
        when {
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.N -> connectivityManager.registerDefaultNetworkCallback(getConnectivityManagerCallbackAPI23())
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.M -> registerNetworkCallbackAPI23()
            Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP -> registerNetworkCallbackAPI21()
            else -> {
                if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                    context.registerReceiver(networkReceiver, IntentFilter("android.net.conn.CONNECTIVITY_CHANGE")) // android.net.ConnectivityManager.CONNECTIVITY_ACTION
                }
            }
        }
    }

    override fun onInactive() {
        super.onInactive()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManager.unregisterNetworkCallback(connectivityManagerCallback)
        } else {
            context.unregisterReceiver(networkReceiver)
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private fun registerNetworkCallbackAPI21() {
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), getConnectivityManagerCallbackAPI21())
    }

    @TargetApi(Build.VERSION_CODES.M)
    private fun registerNetworkCallbackAPI23() {
        connectivityManager.registerNetworkCallback(networkRequestBuilder.build(), getConnectivityManagerCallbackAPI23())
    }

    private fun getConnectivityManagerCallbackAPI21(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onAvailable(network: Network) {
                    postValue(NetworkStatus.Online())
                }

                override fun onLost(network: Network) {
                    postValue(NetworkStatus.Offline())
                }
            }
            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("Accessing wrong API version")
        }
    }

    private fun getConnectivityManagerCallbackAPI23(): ConnectivityManager.NetworkCallback {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManagerCallback = object : ConnectivityManager.NetworkCallback() {
                override fun onCapabilitiesChanged(network: Network, networkCapabilities: NetworkCapabilities) {
                    networkCapabilities.let { capabilities ->
                        if (capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) && capabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED)) {
                            postValue(NetworkStatus.Online())
                        }
                    }
                }
                override fun onLost(network: Network) {
                    postValue(NetworkStatus.Offline())
                }

            }
            return connectivityManagerCallback
        } else {
            throw IllegalAccessError("Accessing wrong API version")
        }
    }

    private val networkReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            updateConnection()
        }
    }

    private fun updateConnection() {
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        postValue(if (activeNetwork?.isConnected == true) {
            NetworkStatus.Online()
        } else {
            NetworkStatus.Offline()
        })
    }

}

sealed class NetworkStatus {
    data class Online(val message: Int = R.string.network_status_online) : NetworkStatus()
    data class Offline(val message: Int = R.string.network_status_offline) : NetworkStatus()
}