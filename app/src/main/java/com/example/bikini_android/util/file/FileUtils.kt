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
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File

/**
 * @author MyeongKi
 */
object FileUtils {
    private const val PATH_ERROR_MESSAGE = "path is null"
    fun checkReadExternalStoragePermission(): Boolean {
        return ContextCompat.checkSelfPermission(
            AppResources.getContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getPath(uri: Uri, context: Context): String {
        var result: String? = null
        val projection = arrayOf(MediaStore.Images.Media.DATA)//FIXME 수정 필요
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
        throw NullPointerException(PATH_ERROR_MESSAGE)
    }

    fun getImageFiles(imageUris: List<Uri>): List<MultipartBody.Part> {
        val imageFiles: MutableList<MultipartBody.Part> = mutableListOf()
        return imageFiles.apply {
            imageUris.forEach { imageUri ->
                val imageFile = File(getPath(imageUri, AppResources.getContext()))
                add(
                    MultipartBody.Part.createFormData(
                        ImageConstants.IMAGES,
                        ImageConstants.IMAGE_NAME,
                        imageFile.asRequestBody(ImageConstants.IMAGE_EXTENSION.toMediaTypeOrNull())
                    )
                )
            }
        }
    }
}