package com.banty.topnews.network.util

import android.content.Context
import android.net.ConnectivityManager

/**
 * Created by Banty on 10/03/19.
 */
class NetworkConnectivityUtil(private val context: Context) {

    /*
    * Returns true if device is connected to Network of any type
    * and false if there is no connection
    * */
    fun isNetworkAvailable(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager!!.activeNetworkInfo
        return activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}