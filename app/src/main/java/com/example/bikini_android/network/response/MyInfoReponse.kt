package com.example.bikini_android.network.response

import com.example.bikini_android.repository.account.UserInfo
import com.google.gson.annotations.SerializedName

/**
 * @author bsgreentea
 */
class MyInfoReponse : JsonResponseWrapper<MyInfoReponse.Result>() {
    data class Result(
        @SerializedName("userInfo")
        val userInfo: UserInfo
    )
}