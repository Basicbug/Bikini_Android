/*
 * OnRequestLoactionPermissionUseCase.kt 2021. 3. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.permission

import android.Manifest
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.bus.event.LocationPermissionEvent

/**
 * @author MyeongKi
 */
class OnRequestLocationPermissionUseCase {
    fun execute(permissions: Array<String>, grantResults: IntArray) {
        val isAccept =
            PermissionUtils.isPermissionGranted(
                permissions,
                grantResults,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
        RxActionBus.post(LocationPermissionEvent(isAccept))
    }
}
