package com.banty.topnews.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock

/**
 * Created by Banty on 10/03/19.
 */
class NetworkConnectivityUtilTest {

    private lateinit var networkConnectivityUtil: NetworkConnectivityUtil

    private val connectivityManager: ConnectivityManager = mock(ConnectivityManager::class.java)

    private val context: Context = mock(Context::class.java)

    private val activeNetworkInfo: NetworkInfo = mock(NetworkInfo::class.java)

    @Before
    fun setUp() {
        `when`(context.getSystemService(Context.CONNECTIVITY_SERVICE)).thenReturn(connectivityManager)
        `when`(connectivityManager.activeNetworkInfo).thenReturn(activeNetworkInfo)
    }

    @Test
    fun shouldReturnTrueIfNetworkIsAvailable() {

        `when`(activeNetworkInfo.isConnected).thenReturn(true)

        networkConnectivityUtil = NetworkConnectivityUtil(context)

        assertTrue(networkConnectivityUtil.isNetworkAvailable())

    }

    @Test
    fun shouldReturnFalseIfNetworkIsAvailable() {

        `when`(activeNetworkInfo.isConnected).thenReturn(false)

        networkConnectivityUtil = NetworkConnectivityUtil(context)

        assertFalse(networkConnectivityUtil.isNetworkAvailable())

    }
}