/*
 * ServiceApiFactory.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.example.bikini_android.BuildConfig
import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author MyeongKi
 */

object ServiceApiFactory {
    fun getApiClient(baseUrl: String): Retrofit {
        val clientBuilder = if (BuildConfig.DEBUG) {
            BikiniDebugApiClient(baseUrl)
        } else {
            BikiniServiceApiClient(baseUrl)
        }
        val okHttpBuilder = OkHttpClient.Builder()
        val retrofitBuilder = clientBuilder.builder(okHttpBuilder)
        return clientBuilder.build(retrofitBuilder)
    }
}