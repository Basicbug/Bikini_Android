/*
 * BikiniDebugApiClient.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.example.bikini_android.app.AppResources
import com.facebook.stetho.Stetho
import com.facebook.stetho.okhttp3.StethoInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit

/**
 * @author MyeongKi
 */

class BikiniDebugApiClient(baseUrl: String) : RestApiClient(baseUrl) {
    override fun builder(builder: OkHttpClient.Builder): Retrofit.Builder {
        val log = HttpLoggingInterceptor()
        log.setLevel(HttpLoggingInterceptor.Level.BODY)

        Stetho.initializeWithDefaults(AppResources.getContext())
        builder.addNetworkInterceptor(StethoInterceptor())
        builder.addInterceptor(log)
        return super.builder(builder)
    }
}
