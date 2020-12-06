/*
 * SampleService.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.response.DummyImageResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @author MyeongKi
 */

interface DummyService {
    @GET("path")
    fun getImages(): Single<DummyImageResponse>
}