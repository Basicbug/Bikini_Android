/*
 * BitmapUtils.kt 2021. 5. 18
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.image

import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import com.basicbug.core.app.AppResources

/**
 * @author MyeongKi
 */
abstract class BitmapUtils {
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