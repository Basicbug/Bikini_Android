/*
 * FileUtils.kt 2021. 3. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.file

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.net.Uri
import android.provider.MediaStore
import androidx.core.content.ContextCompat
import com.example.bikini_android.app.AppResources

/**
 * @author MyeongKi
 */
object FileUtils {

    fun checkReadExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getPath(uri: Uri, context: Context): String {
        var result: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)
        val cursor = context.contentResolver.query(uri, projection, null, null, null)
        cursor?.let {
            if (it.moveToFirst()) {
                val index: Int = it.getColumnIndexOrThrow(projection[0])
                result = it.getString(index)
            }
        }
        cursor?.close()
        result?.let {
            return it
        }
        throw NullPointerException("path is null")
    }
}