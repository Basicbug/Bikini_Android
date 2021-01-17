package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author bsgreentea
 */
class DefaultResponse : JsonResponseWrapper<DefaultResponse.Result>() {
    data class Result(
        @SerializedName("status")
        val status: String
    )
}