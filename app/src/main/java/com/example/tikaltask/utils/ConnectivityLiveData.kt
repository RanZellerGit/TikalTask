package com.example.tikaltask.utils

import android.arch.lifecycle.LiveData
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkInfo
import android.net.NetworkRequest
import android.os.Build

class ConnectivityLiveData(private val mConnectivityManager: ConnectivityManager)
    : LiveData<Boolean>() {

    private val mNetworkCallback = object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network?) {
            postValue(true)
        }

        override fun onLost(network: Network?) {
            postValue(false)
        }
    }

    override fun onActive() {
        super.onActive()

        val activeNetwork: NetworkInfo? = mConnectivityManager.activeNetworkInfo
        postValue(activeNetwork?.isConnectedOrConnecting == true)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            mConnectivityManager.registerDefaultNetworkCallback(mNetworkCallback)
        } else {
            val builder = NetworkRequest.Builder()
            mConnectivityManager.registerNetworkCallback(builder.build(), mNetworkCallback)
        }
    }

    override fun onInactive() {
        super.onInactive()
        mConnectivityManager.unregisterNetworkCallback(mNetworkCallback)
    }
}