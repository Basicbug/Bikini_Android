package com.example.bikini_android.network.response

import com.basicbug.network.response.JsonResponseWrapper
import com.example.bikini_android.repository.account.UserInfo
import com.google.gson.annotations.SerializedName

/**
 * @author bsgreentea
 */
class MyInfoResponse : JsonResponseWrapper<MyInfoResponse.Result>() {
    data class Result(
        @SerializedName("userInfo")
        val userInfo: UserInfo
    )
}