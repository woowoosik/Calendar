package com.woo.calendarapp

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import javax.inject.Inject


class PermissionCheck @Inject constructor(
        private val context: Context
    ) {

    val permission = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION,
    )


    fun isAllPermissionsGranted(): Boolean = permission.all { permission ->
        ContextCompat.checkSelfPermission(context, permission) ==
                PackageManager.PERMISSION_GRANTED
    }


}