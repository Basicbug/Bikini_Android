/*
 * GoogleMapUtils.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.map

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

/**
 * @author MyeongKi
 */

object GoogleMapUtils {
    fun getFeedMarkerOption(iconView: View, position: LatLng): MarkerOptions {
        return MarkerOptions()
            .position(position)
            .icon(BitmapDescriptorFactory.fromBitmap(convertBitmap(iconView)))
    }

    private fun convertBitmap(feedMarkerView: View): Bitmap {
        return Bitmap.createBitmap(
            feedMarkerView.measuredWidth,
            feedMarkerView.measuredHeight,
            Bitmap.Config.ARGB_8888
        ).also { bitmap ->
            Canvas(bitmap).run {
                feedMarkerView.draw(this)
            }
        }
    }
}