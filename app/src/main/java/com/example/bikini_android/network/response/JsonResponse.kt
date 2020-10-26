/*
 * JsonResponse.kt 2020. 10. 26
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */


data class JsonResponse<T>(
    @SerializedName("result")
    val result: T?,
    @SerializedName("message")
    val message: RestApiMessage
) {
    fun validate(): Boolean {
        if (result == null) {
            return false
        }
        return true
    }
}