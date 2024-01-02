package com.example.test_in_kotlin.data.transactions.local

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import com.example.test_in_kotlin.data.transactions.TransactionRepository
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.first

sealed class ConnectionState {
    object Available : ConnectionState()
    object Unavailable : ConnectionState()
}

fun Context.getCurrentConnectivityState(): ConnectionState {
    val cm = this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val connected = cm.activeNetwork.let {
        cm.getNetworkCapabilities(it)?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            ?: false
    }
    return if (connected) ConnectionState.Available else ConnectionState.Unavailable
}

fun Context.observeConnectivityAsFlow() = callbackFlow {
    val cm =
        this@observeConnectivityAsFlow.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val cb = NetworkCallback { connectionState -> trySend(connectionState) }
    val req =
        NetworkRequest.Builder().addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET).build()

    cm.registerNetworkCallback(req, cb)

    val currState = getCurrentConnectivityState()
    trySend(currState)

    awaitClose {
        cm.unregisterNetworkCallback(cb)
    }
}

fun NetworkCallback(callback: (ConnectionState) -> Unit): ConnectivityManager.NetworkCallback {
    return object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            callback(ConnectionState.Available)
        }

        override fun onLost(network: Network) {
            callback(ConnectionState.Unavailable)
        }
    }
}

suspend fun checkIfAnyCached(repo: TransactionRepository):Boolean {
    return repo.getTransactions().first() != null
}