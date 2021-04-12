/*
 * RestApiClient.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.example.bikini_android.app.AppResources
import com.example.bikini_android.network.HostSelectionInterceptor
import com.example.bikini_android.network.ResponseCacheFactory
import com.example.bikini_android.ui.settings.FlipperSettingImpl
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * @author MyeongKi
 */
abstract class RestApiClient(private val requestUrl: String) : ApiClient {

    override fun builder(builder: OkHttpClient.Builder): Retrofit.Builder {
        builder
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HostSelectionInterceptor())
            .cache(ResponseCacheFactory().createCache(AppResources.getContext()))
        return Retrofit.Builder().client(FlipperSettingImpl.addFlipperNetworkPlugin(builder).build())
    }

    override fun build(builder: Retrofit.Builder): Retrofit {
        return builder
            .baseUrl(requestUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }
}