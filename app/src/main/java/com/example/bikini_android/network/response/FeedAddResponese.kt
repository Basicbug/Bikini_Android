package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author bsgreentea
 */
class FeedAddResponese : JsonResponseWrapper<FeedAddResponese.Result>() {
    data class Result(
        @SerializedName("status")
        val status: String
    )
}