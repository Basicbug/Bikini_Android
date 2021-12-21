/*
 * ApiFactory.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.client

import okhttp3.Authenticator
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author MyeongKi
 */

object ApiFactory {
    fun getMainApiClient(
        baseUrl: String,
        tokenAuthenticator: Authenticator,
        interceptors: List<Interceptor>
    ): Retrofit {
        val clientBuilder = DefaultApiClient(
            baseUrl,
            tokenAuthenticator,
            interceptors
        )
        return getApiClient(clientBuilder)
    }

    fun getInvalidApiClient(
        baseUrl: String,
        interceptors: List<Interceptor>
    ): Retrofit {
        val clientBuilder = InvalidAuthApiClient(baseUrl, interceptors)
        return getApiClient(clientBuilder)
    }

    private fun getApiClient(clientBuilder: RestApiClient): Retrofit {
        val okHttpBuilder = OkHttpClient.Builder()
        val retrofitBuilder = clientBuilder.builder(okHttpBuilder)
        return clientBuilder.build(retrofitBuilder)
    }
}