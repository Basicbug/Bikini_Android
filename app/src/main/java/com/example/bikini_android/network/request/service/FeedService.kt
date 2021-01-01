/*
 * FeedService.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.request.param.NearLocationFeedParameter
import com.example.bikini_android.network.response.FeedsResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author MyeongKi
 */

interface FeedService {
    @GET("/v1/feed/list/{userId}")
    fun getUserFeeds(@Path("userId") userId: String): Single<FeedsResponse>

    @GET("path")
    fun getNearLocationFeeds(@QueryMap locationParameter: NearLocationFeedParameter): Single<FeedsResponse>
}