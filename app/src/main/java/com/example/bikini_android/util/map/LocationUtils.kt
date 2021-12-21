/*
 * LocationUtils.kt 2021. 1. 19
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.map

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import androidx.core.content.ContextCompat
import com.basicbug.core.app.AppResources
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.model.LatLng

/**
 * @author MyeongKi
 */
object LocationUtils {
    private var lastCurrentLocation: Location? = null

    fun checkLocationPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getCurrentLatLng(): LatLng? {
        return lastCurrentLocation?.let {
            LatLng(it.latitude, it.longitude)
        }
    }

    fun initCurrentLocationEvent() {
        if (checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(AppResources.getContext()).lastLocation.addOnCompleteListener {
                lastCurrentLocation = it.result
            }
        }
    }

    fun addOnCompleteCurrentLocationListener(action: (Location?) -> Unit) {
        if (checkLocationPermission()) {
            LocationServices.getFusedLocationProviderClient(AppResources.getContext()).lastLocation.addOnCompleteListener {
                lastCurrentLocation = it.result
                action(it.result)
            }
        }
    }

    fun getDistanceBetween(startLatLng: LatLng?, endLatLng: LatLng?): Float {
        return if (startLatLng == null || endLatLng == null) {
            0f
        } else {
            val diagonalDistance = FloatArray(1)
            Location.distanceBetween(
                startLatLng.latitude,
                startLatLng.longitude,
                endLatLng.latitude,
                endLatLng.longitude,
                diagonalDistance
            )
            diagonalDistance[0] / 1000
        }
    }
}