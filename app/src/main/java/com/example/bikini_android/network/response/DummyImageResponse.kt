/*
 * DummyResponse.kt 2020. 11. 28
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */

class DummyImageResponse : JsonResponseWrapper<DummyImageResponse.Result>() {
    data class Result(
        @SerializedName("images")
        val images: List<String>
    )
}