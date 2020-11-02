/*
 * ApiClient.kt 2020. 10. 27
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

interface ApiClient {
    fun builder(builder: OkHttpClient.Builder): Retrofit.Builder
    fun build(builder: Retrofit.Builder): Retrofit
}