/*
 * BitmapUtils.kt 2021. 5. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.image

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.view.View
import com.example.bikini_android.app.AppResources
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

    fun getImageMatrix(imageUri: Uri): Matrix {
        val matrix = Matrix()
        val exifStream = AppResources.getContentResolver().openInputStream(imageUri)

        exifStream?.let {
            val oldExif = ExifInterface(it)
            val orientation =
                oldExif.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> {
                    matrix.postRotate(90f)
                }
                ExifInterface.ORIENTATION_ROTATE_180 -> {
                    matrix.postRotate(180f)
                }
                ExifInterface.ORIENTATION_ROTATE_270 -> {
                    matrix.postRotate(270f)
                }
                else -> Unit
            }
        }
        return matrix
    }
}