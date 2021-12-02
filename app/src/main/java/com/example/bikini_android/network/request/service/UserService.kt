package com.example.bikini_android.network.request.service

import com.example.bikini_android.network.response.MyInfoReponse
import com.example.bikini_android.network.response.UserUpdateResponse
import com.example.bikini_android.repository.account.UserInfo
import io.reactivex.Single
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.PUT

/**
 * @author bsgreentea
 */
interface UserService {

    @PUT("/v1/user/update")
    fun updateUserInfo(
        @Header("X-AUTH-TOKEN") jwt: String,
        @Body userInfo: UserInfo
    ): Single<UserUpdateResponse>

    @GET("/v1/user/about/me")
    fun getMyInfo(
        @Header("X-AUTH-TOKEN") jwt: String
    ): Single<MyInfoReponse>
}