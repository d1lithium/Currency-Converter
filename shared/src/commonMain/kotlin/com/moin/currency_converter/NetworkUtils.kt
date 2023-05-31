package com.moin.currency_converter

expect class NetworkUtils {
    fun isInternetConnected(): Boolean
    fun isInternetPermissionAllowed(): Boolean
}