/*
 * ImageResponse.kt 2021. 3. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */
class ImagesResponse : JsonResponseWrapper<ImagesResponse.Result>() {
    data class Result(
        @SerializedName("id")
        val id: String,
        @SerializedName("url")
        val url: String
    )
}