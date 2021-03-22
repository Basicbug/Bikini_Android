package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.response.LoginResponse
import io.reactivex.Single
import retrofit2.http.GET

/**
 * @author bsgreentea
 */
interface AuthService {

    @GET("/v1/auth/login/naver")
    fun loginNaver(accessToken: String): Single<LoginResponse>

//    @GET("/v1/auth/redirect")
//    fun redirectNaver()

}