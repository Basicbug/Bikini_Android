/*
 * JsonResponse.kt 2020. 10. 26
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.response

import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */

abstract class JsonResponseWrapper<T> {
    @SerializedName("result")
    val result: T? = null

    @SerializedName("message")
    val message: String = ""

    @SerializedName("code")
    val code: String = ""

    fun validate(): Boolean {
        if (result == null) {
            return false
        }
        return true
    }
}
