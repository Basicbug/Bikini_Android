/*
 * BitmapUtils.kt 2021. 5. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory

/**
 * @author MyeongKi
 */
object BitmapUtils {
    fun convertBitmapDescriptor(view: View): BitmapDescriptor {
        return BitmapDescriptorFactory.fromBitmap(Bitmap.createBitmap(
            view.measuredWidth,
            view.measuredHeight,
            Bitmap.Config.ARGB_8888
        ).also { bitmap ->
            Canvas(bitmap).run {
                view.draw(this)
            }
        }
        )
    }
}