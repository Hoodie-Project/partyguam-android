package com.party.common

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.HttpURLConnection
import java.net.URL

interface ConnectivityObserver {
    fun getFlow(): Flow<Status>

    enum class Status {
        Available, UnAvailable, Losing, Lost, Init
    }
}

class NetworkConnectivityObserver(
    context: Context
) : ConnectivityObserver {

    private val connectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    override fun getFlow(): Flow<ConnectivityObserver.Status> {
        return callbackFlow {
            val callback = object : ConnectivityManager.NetworkCallback() {

                fun checkInternetAndSendWithDelay() {
                    launch {
                        delay(300) // 딜레이 추가: 네트워크 전환 시간 고려
                        val hasInternet = hasInternetConnection()
                        val status = if (hasInternet) ConnectivityObserver.Status.Available
                        else ConnectivityObserver.Status.UnAvailable
                        send(status)
                    }
                }

                override fun onAvailable(network: Network) {
                    checkInternetAndSendWithDelay()
                }

                override fun onLost(network: Network) {
                    checkInternetAndSendWithDelay()
                }

                override fun onUnavailable() {
                    checkInternetAndSendWithDelay()
                }

                override fun onLosing(network: Network, maxMsToLive: Int) {
                    checkInternetAndSendWithDelay()
                }
            }

            connectivityManager.registerDefaultNetworkCallback(callback)

            awaitClose {
                connectivityManager.unregisterNetworkCallback(callback)
            }
        }.distinctUntilChanged()
    }
}


suspend fun hasInternetConnection(): Boolean = withContext(Dispatchers.IO) {
    try {
        val url = URL("https://clients3.google.com/generate_204")
        val connection = url.openConnection() as HttpURLConnection
        connection.setRequestProperty("User-Agent", "Android")
        connection.connectTimeout = 1500
        connection.connect()
        connection.responseCode == 204
    } catch (e: Exception) {
        false
    }
}
