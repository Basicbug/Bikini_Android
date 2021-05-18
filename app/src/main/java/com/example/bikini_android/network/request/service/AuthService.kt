package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.GET
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
    ): Single<LoginResponse>

    companion object {
        private const val NAVER = "naver"
    }
}