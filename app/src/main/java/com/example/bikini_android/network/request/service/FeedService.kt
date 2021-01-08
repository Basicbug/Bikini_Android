/*
 * FeedService.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.request.param.NearLocationFeedParameter
import com.example.bikini_android.network.response.FeedAddResponese
import com.example.bikini_android.network.response.FeedsResponse
import com.example.bikini_android.repository.feed.Feed
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
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

    @GET("path")
    fun getNearLocationFeeds(@QueryMap locationParameter: NearLocationFeedParameter): Single<FeedsResponse>

    @GET("/v1/feed/list")
    fun getAllFeeds(): Single<FeedsResponse>

    @FormUrlEncoded
    @POST("v1/feed/add")
    fun postFeed(
        @Field("feedRequestDto") feed: Feed
    ): Single<FeedAddResponese>
}