/*
 * ApiClient.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.client

import okhttp3.OkHttpClient
import retrofit2.Retrofit

/**
 * @author MyeongKi
 */

interface ApiClient {
    fun setBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder
    fun builder(builder: OkHttpClient.Builder): Retrofit.Builder
    fun build(builder: Retrofit.Builder): Retrofit
}