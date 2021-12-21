/*
 * AuthService.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.request

import com.basicbug.network.response.TokenResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * @author bsgreentea
 */
interface AuthService {

    @GET("/v1/auth/login/{provider}")
    fun loginNaver(
        @Path("provider") provider: String = NAVER,
        @Query("accessToken") accessToken: String
    ): Single<TokenResponse>

    @POST("/v1/auth/refresh")
    fun refreshAccessToken(
        @Query("accessToken") accessToken: String,
        @Query("refreshToken") refreshToken: String
    ): Single<TokenResponse>

    companion object {
        private const val NAVER = "naver"
    }
}