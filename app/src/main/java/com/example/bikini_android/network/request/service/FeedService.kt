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
import retrofit2.http.*

/**
 * @author MyeongKi
 */

interface FeedService {
    @GET("/v1/feed/list/{userId}")
    fun getUserFeeds(
        @Header("X-AUTH-TOKEN") jwt: String,
        @Path("userId") userId: String
    ): Single<FeedsResponse>

    @GET("/v1/feed/list/top/{limit}")
    fun getRankFeeds(
        @Header("X-AUTH-TOKEN") jwt: String,
        @Path("limit") limit: Int
    ): Single<FeedsResponse>

    @GET("/v1/feed/nearby")
    fun getNearbyLocationFeeds(
        @Header("X-AUTH-TOKEN") jwt: String,
        @QueryMap nearbyParameter: NearbyFeedParameter,
    ): Single<FeedsResponse>

    @GET("/v1/feed/list")
    fun getAllFeeds(@Header("X-AUTH-TOKEN") jwt: String): Single<FeedsResponse>

    @POST("v1/feed/add")
    fun addFeed(
        @Header("X-AUTH-TOKEN") jwt: String,
        @Body feed: Feed
    ): Single<DefaultResponse>
}