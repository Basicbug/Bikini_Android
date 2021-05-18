/*
 * GoogleMapUtils.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.map

import android.location.Location
import com.example.bikini_android.util.map.LocationUtils.getDistanceBetween
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.VisibleRegion

/**
 * @author MyeongKi
 */

object GoogleMapUtils {

    fun getVisibleRadius(visibleRegion: VisibleRegion): Float {
        val diagonalDistance = FloatArray(1)
        Location.distanceBetween(
            visibleRegion.farLeft.latitude,
            visibleRegion.farLeft.longitude,
            visibleRegion.nearRight.latitude,
            visibleRegion.nearRight.longitude,
            diagonalDistance
        )
        return getDistanceBetween(
            LatLng(visibleRegion.farLeft.latitude, visibleRegion.farLeft.longitude),
            LatLng(visibleRegion.nearRight.latitude, visibleRegion.nearRight.longitude)
        ) / 2
    }
}