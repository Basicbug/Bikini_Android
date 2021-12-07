/*
 * FileUtilsImpl.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.file

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import com.basicbug.core.app.AppResources
import com.basicbug.core.util.file.FileConstants
import com.basicbug.core.util.file.FileUtils
import com.example.bikini_android.manager.PreferenceManagerImpl
import com.example.bikini_android.util.image.BitmapUtilsImpl
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import java.io.FileOutputStream

/**
 * @author MyeongKi
 */
object FileUtilsImpl : FileUtils() {
    fun getImageMultiParts(imageUris: List<Uri>): List<MultipartBody.Part> {
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
                        BitmapUtilsImpl.getImageMatrix(imageUri),
                        true
                    ).run {
                        FileOutputStream(localImgFile).use {
                            this.compress(
                                Bitmap.CompressFormat.JPEG,
                                PreferenceManagerImpl.getImageCompressionRate().rate,
                                it
                            )
                            it.flush()
                        }
                    }
                }
                add(
                    MultipartBody.Part.createFormData(
                        FileConstants.IMAGES,
                        FileConstants.IMAGE_NAME,
                        localImgFile.asRequestBody(FileConstants.IMAGE_EXTENSION.toMediaTypeOrNull())
                    )
                )
            }
        }
    }
}