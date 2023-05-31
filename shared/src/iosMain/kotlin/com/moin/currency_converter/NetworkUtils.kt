package com.moin.currency_converter

import kotlinx.cinterop.CValuesRef
import kotlinx.cinterop.UIntVarOf
import kotlinx.cinterop.ptr
import kotlinx.cinterop.usePinned
import platform.Foundation.NSData
import platform.Foundation.NSURL
import platform.SystemConfiguration.SCNetworkReachabilityCreateWithName
import platform.SystemConfiguration.SCNetworkReachabilityFlags
import platform.SystemConfiguration.SCNetworkReachabilityFlagsVar
import platform.SystemConfiguration.SCNetworkReachabilityGetFlags
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsConnectionRequired
import platform.SystemConfiguration.kSCNetworkReachabilityFlagsReachable
import platform.UIKit.UIApplication

actual class NetworkUtils {
    actual fun isInternetConnected(): Boolean {
        val reachability = SCNetworkReachabilityCreateWithName(null, "www.google.com")
        var flags = SCNetworkReachabilityFlags as SCNetworkReachabilityFlagsVar
        if (reachability != null) {
           reachability.usePinned { reachabilityPtr ->
                SCNetworkReachabilityGetFlags(reachabilityPtr.get(), flags.ptr)
            }
        }
        return flags.toString().contains(kSCNetworkReachabilityFlagsReachable.toString()) && flags.toString().contains(kSCNetworkReachabilityFlagsConnectionRequired.toString())
    }

    actual fun isInternetPermissionAllowed(): Boolean {
        return true // Internet permission is not required on iOS
    }
}
