/*
 * ServiceApiFactory.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author MyeongKi
 */

object ApiFactory {
    fun getMainApiClient(baseUrl: String): Retrofit {
        val clientBuilder = DefaultApiClient(baseUrl)
        val okHttpBuilder = OkHttpClient.Builder()
        val retrofitBuilder = clientBuilder.builder(okHttpBuilder)
        return clientBuilder.build(retrofitBuilder)
    }
}