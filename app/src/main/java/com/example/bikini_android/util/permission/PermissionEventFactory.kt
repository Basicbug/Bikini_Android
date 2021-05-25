/*
 * PermissionEventFactory.kt 2021. 5. 24
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.permission

import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.bus.event.ExternalReadAndWriteStoragePermissionEvent
import com.example.bikini_android.util.bus.event.LocationPermissionEvent

/**
 * @author MyeongKi
 */
object PermissionEventFactory {

    fun create(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ): RxAction? {
        val isAccept = PermissionUtils.isPermissionGranted(permissions, grantResults)
        return when (requestCode) {
            PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE -> {
                LocationPermissionEvent(isAccept)
            }
            PermissionUtils.READ_AND_WRITE_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE -> {
                ExternalReadAndWriteStoragePermissionEvent(isAccept)
            }
            else -> {
                null
            }
        }
    }
}