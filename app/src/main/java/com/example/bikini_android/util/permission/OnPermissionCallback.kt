/*
 * OnPermissionCallback.kt 2020. 11. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.permission

import android.Manifest
import androidx.core.app.ActivityCompat
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.LocationPermissionEvent
import com.example.bikini_android.util.permission.PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE

/**
 * @author MyeongKi
 */

interface OnPermissionCallback : ActivityCompat.OnRequestPermissionsResultCallback {
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (requestCode != LOCATION_PERMISSION_REQUEST_CODE) {
            return
        }
        val isAccept =
            PermissionUtils.isPermissionGranted(permissions, grantResults, Manifest.permission.ACCESS_FINE_LOCATION)
        RxActionBus.post(LocationPermissionEvent(isAccept))
    }
}