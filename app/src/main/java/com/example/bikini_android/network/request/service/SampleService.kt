/*
 * SampleService.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.request.param.SampleParameter
import com.example.bikini_android.network.response.SampleResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.QueryMap

/**
 * @author MyeongKi
 */

interface SampleService {
    @GET("path")
    fun getSample(@QueryMap parameter: SampleParameter): Single<SampleResponse>
}