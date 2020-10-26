/*
 * SampleResponse.kt 2020. 10. 26
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */

class SampleResponse : JsonResponseWrapper<SampleResponse.Result>() {
    data class Result(
        @SerializedName("sampleData")
        val sample: String
    )
}