/*
 * OnRequestReadExternalStoragePermissionUseCase.kt 2021. 3. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.permission

import android.Manifest
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.ReadExternalStoragePermissionEvent

/**
 * @author MyeongKi
 */
class OnRequestReadExternalStoragePermissionUseCase {
    fun execute(permissions: Array<String>, grantResults: IntArray) {
        val isAccept =
            PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.READ_EXTERNAL_STORAGE
            )
        RxActionBus.post(ReadExternalStoragePermissionEvent(isAccept))
    }
}