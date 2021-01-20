/*
 * LocationUtils.kt 2021. 1. 19
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.map

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import androidx.core.content.ContextCompat
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.repository.feed.LocationInfo

/**
 * @author MyeongKi
 */
object LocationUtils {
    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getCurrentLocationInfo(): LocationInfo? {
        return getCurrentLocation()?.let {
            LocationInfo(it.latitude, it.longitude)
        }
    }

    fun getCurrentLocation(): Location? {
        if (checkLocationPermission()) {
            (AppResources.getContext()
                .getSystemService(Context.LOCATION_SERVICE) as LocationManager).run {
                return this.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
                    ?: this.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
        }
        return null
    }
}