/*
 * FeedsResponse.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.response

import com.example.bikini_android.repository.feed.Feed
import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */

class FeedsResponse : JsonResponseWrapper<FeedsResponse.Result>() {
    data class Result(
        @SerializedName("feeds")
        val feeds: List<Feed>
    )
}
