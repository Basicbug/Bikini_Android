package com.example.bikini_android.network.response

/**
 * @author bsgreentea
 */
class LoginResponse : JsonResponseWrapper<LoginResponse.Result>() {
    data class Result(
        val result: String
    )
}