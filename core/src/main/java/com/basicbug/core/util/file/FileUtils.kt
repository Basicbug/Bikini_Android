/*
 * FileUtils.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.file

import android.Manifest
import android.annotation.SuppressLint
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import androidx.core.content.ContextCompat
import com.basicbug.core.app.AppResources
import com.basicbug.core.manager.PreferenceManager
import com.basicbug.core.util.image.BitmapUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * @author MyeongKi
 */
abstract class FileUtils {

    fun checkReadExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getCameraImageFile(): File? {
        val files = AppResources.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        return files?.listFiles()?.last()
    }

    @SuppressLint("SimpleDateFormat")
    fun createCameraImageFile(): File {
        val storageDir: File? =
            AppResources.getContext().getExternalFilesDir(Environment.DIRECTORY_PICTURES)
        storageDir?.deleteOnExit()
        return File.createTempFile(
            "JPEG_capture", /* prefix */
            ".jpg", /* suffix */
            storageDir /* directory */
        ).apply {
            deleteOnExit()
        }
    }



    protected companion object {
        const val LOCAL_IMAGE_NAME = "localImgFile"
        const val DEFAULT_IMAGE_FILE_TYPE = ".jpeg"
    }
}