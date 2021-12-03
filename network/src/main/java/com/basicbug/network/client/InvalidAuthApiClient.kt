/*
 * InvalidAuthApiClient.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.client

import com.basicbug.core.app.AppResources
import com.basicbug.network.ResponseCacheFactory
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author MyeongKi
 */
class InvalidAuthApiClient(
    baseUrl: String,
    private val interceptors: List<Interceptor>
) : RestApiClient(baseUrl) {
    override fun setBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        val resultBuilder = builder
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
        interceptors.forEach {
            resultBuilder.addInterceptor(it)
        }
        return resultBuilder
            .cache(ResponseCacheFactory().createCache(AppResources.getContext()))
    }
}