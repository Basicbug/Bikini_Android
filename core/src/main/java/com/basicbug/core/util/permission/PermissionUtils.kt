/*
 * PermissionUtils.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.permission

import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.basicbug.core.ui.dialog.DialogController

/**
 * @author MyeongKi
 */

object PermissionUtils {
    const val LOCATION_PERMISSION_REQUEST_CODE = 1
    const val READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2
    const val CAMERA_READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 3

    @JvmStatic
    fun requestPermission(
        activity: AppCompatActivity,
        requestId: Int,
        permissions: Array<String>,
        finishActivity: Boolean
    ) {
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    requestId
                )
                return
            }
        }
        DialogController.showRationaleDialog(activity, requestId, finishActivity, permissions)
    }

    @JvmStatic
    fun isPermissionGranted(
        grantPermissions: Array<String>, grantResults: IntArray
    ): Boolean {
        for (i in grantPermissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }
}