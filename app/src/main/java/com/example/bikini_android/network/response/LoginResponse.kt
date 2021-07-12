package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author bsgreentea
 */
class LoginResponse : JsonResponseWrapper<LoginResponse.Result>() {
    data class Result(
        @SerializedName("token")
        val token: String
    )
}