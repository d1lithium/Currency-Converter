package com.moin.currency_converter

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import androidx.core.content.ContextCompat
import androidx.test.core.app.ApplicationProvider

actual class NetworkUtils() {
    private val context = ApplicationProvider.getApplicationContext<Context>()

    @SuppressLint("MissingPermission")
    actual fun isInternetConnected(): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        val activeNetworkInfo = connectivityManager?.activeNetworkInfo
        return activeNetworkInfo?.isConnectedOrConnecting == true
    }

    actual fun isInternetPermissionAllowed(): Boolean {
        val permission = Manifest.permission.INTERNET
        val result = ContextCompat.checkSelfPermission(context, permission)
        return result == PackageManager.PERMISSION_GRANTED
    }
}