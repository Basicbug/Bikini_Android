/*
 * FileUtils.kt 2021. 3. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.file

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.core.content.ContextCompat
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.util.image.BitmapUtils
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * @author MyeongKi
 */
object FileUtils {
    private const val LOCAL_IMAGE_NAME = "localImgFile"
    private const val DEFAULT_IMAGE_FILE_TYPE = ".jpeg"
    fun checkReadExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getImageFiles(imageUris: List<Uri>): List<MultipartBody.Part> {
        val imageFiles: MutableList<MultipartBody.Part> = mutableListOf()
        return imageFiles.apply {
            imageUris.forEach { imageUri ->
                val localImgFile: File =
                    File.createTempFile(LOCAL_IMAGE_NAME, DEFAULT_IMAGE_FILE_TYPE).apply {
                        deleteOnExit()
                    }
                BitmapFactory.decodeStream(
                    AppResources.getContentResolver().openInputStream(imageUri)
                ).run {
                    Bitmap.createBitmap(
                        this,
                        0,
                        0,
                        this.width,
                        this.height,
                        BitmapUtils.getImageMatrix(imageUri),
                        true
                    ).run {
                        FileOutputStream(localImgFile).use {
                            this.compress(Bitmap.CompressFormat.JPEG, 25, it)
                            it.flush()
                        }
                    }
                }
                add(
                    MultipartBody.Part.createFormData(
                        ImageConstants.IMAGES,
                        ImageConstants.IMAGE_NAME,
                        localImgFile.asRequestBody(ImageConstants.IMAGE_EXTENSION.toMediaTypeOrNull())
                    )
                )
            }
        }
    }
}