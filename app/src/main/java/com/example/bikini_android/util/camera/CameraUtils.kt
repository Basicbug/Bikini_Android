/*
 * CameraUtils.kt 2021. 8. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.camera

import android.Manifest
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import com.example.bikini_android.app.AppResources

/**
 * @author MyeongKi
 */
object CameraUtils {
    fun checkCameraPermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.CAMERA
        ) == PackageManager.PERMISSION_GRANTED
    }
}