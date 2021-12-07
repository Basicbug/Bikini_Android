/*
 * RestApiClient.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * @author MyeongKi
 */
abstract class RestApiClient(private val requestUrl: String) : ApiClient {
    override fun builder(builder: OkHttpClient.Builder): Retrofit.Builder {
        return Retrofit.Builder().client(setBuilder(builder).build())
    }

    override fun build(builder: Retrofit.Builder): Retrofit {
        return builder
            .baseUrl(requestUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}