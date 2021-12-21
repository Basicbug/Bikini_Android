/*
 * PermissionEventFactory.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.permission

import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.bus.event.CameraAndExternalReadAndWriteStoragePermissionEvent
import com.basicbug.core.util.bus.event.ExternalReadAndWriteStoragePermissionEvent
import com.basicbug.core.util.bus.event.LocationPermissionEvent

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
            PermissionUtils.READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE -> {
                ExternalReadAndWriteStoragePermissionEvent(isAccept)
            }
            PermissionUtils.CAMERA_READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE -> {
                CameraAndExternalReadAndWriteStoragePermissionEvent(isAccept)
            }
            else -> {
                null
            }
        }
    }
}