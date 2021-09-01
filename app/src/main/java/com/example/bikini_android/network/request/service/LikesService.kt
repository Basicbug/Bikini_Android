/*
 * LikeItService.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.request.param.LikesParameter
import com.example.bikini_android.network.response.LikesResponse
import io.reactivex.Single
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * @author MyeongKi
 */
interface LikesService {
    @POST("/v1/likes/add")
    fun addLikes(
        @Header("X-AUTH-TOKEN") jwt: String,
        @QueryMap likesParameter: LikesParameter
    ): Single<LikesResponse>

    @DELETE("/v1/likes/remove")
    fun removeLikes(
        @Header("X-AUTH-TOKEN") jwt: String,
        @QueryMap likesParameter: LikesParameter
    ): Single<LikesResponse>

    @GET("/v1/likes/check")
    fun checkLikes(
        @Header("X-AUTH-TOKEN") jwt: String,
        @QueryMap likesParameter: LikesParameter
    ): Single<LikesResponse>
}