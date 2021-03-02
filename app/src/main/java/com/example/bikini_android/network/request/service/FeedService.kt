/*
 * FeedService.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.request.param.NearbyFeedParameter
import com.example.bikini_android.network.response.DefaultResponse
import com.example.bikini_android.network.response.FeedsResponse
import com.example.bikini_android.repository.feed.Feed
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.QueryMap

/**
 * @author MyeongKi
 */

interface FeedService {
    @GET("/v1/feed/list/{userId}")
    fun getUserFeeds(@Path("userId") userId: String): Single<FeedsResponse>

    @GET("/v1/feed/list/top/{limit}")
    fun getRankFeeds(@Path("limit") limit: Int): Single<FeedsResponse>

    @GET("/v1/feed/nearby")
    fun getNearbyLocationFeeds(@QueryMap nearbyParameter: NearbyFeedParameter): Single<FeedsResponse>

    @GET("/v1/feed/list")
    fun getAllFeeds(): Single<FeedsResponse>

    @POST("v1/feed/add")
    fun addFeed(
        @Body feed: Feed
    ): Single<DefaultResponse>
}