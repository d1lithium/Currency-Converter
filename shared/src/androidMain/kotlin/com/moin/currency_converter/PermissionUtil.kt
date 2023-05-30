package com.moin.currency_converter

import android.Manifest
import android.content.Context
import android.os.Build
import pub.devrel.easypermissions.EasyPermissions

object PermissionUtil {

    fun hasInternetPermissions(context: Context)=
        if(Build.VERSION.SDK_INT < Build.VERSION_CODES.Q){
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.INTERNET,
            )
        }
        else{
            EasyPermissions.hasPermissions(
                context,
                Manifest.permission.INTERNET,
            )


        }

}