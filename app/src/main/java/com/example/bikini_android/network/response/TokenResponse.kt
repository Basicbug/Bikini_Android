package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author bsgreentea
 */
class TokenResponse : JsonResponseWrapper<TokenResponse.Result>() {
    data class Result(
        @SerializedName("accessToken")
        val accessToken: String,
        @SerializedName("refreshToken")
        val refreshToken: String
    )
}