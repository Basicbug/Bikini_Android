/*
 * ImageService.kt 2021. 3. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.response.ImagesResponse
import io.reactivex.Single
import okhttp3.MultipartBody
import retrofit2.http.Header
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part

/**
 * @author MyeongKi
 */
interface ImagesService {
    @Multipart
    @POST("/v1/feed/upload/images")
    fun uploadImages(
        @Header("X-AUTH-TOKEN") jwt: String,
        @Part imageFiles: List<MultipartBody.Part>
    ): Single<ImagesResponse>
}