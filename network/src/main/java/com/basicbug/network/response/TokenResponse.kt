/*
 * TokenResponse.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.response

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