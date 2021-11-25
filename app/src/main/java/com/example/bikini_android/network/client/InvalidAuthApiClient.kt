/*
 * InvalidAuthApiClient.kt 2021. 10. 16
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.basicbug.core.app.AppResources
import com.example.bikini_android.network.HostSelectionInterceptor
import com.example.bikini_android.network.ResponseCacheFactory
import com.example.bikini_android.ui.settings.FlipperSettingImpl
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * @author MyeongKi
 */
class InvalidAuthApiClient(baseUrl: String) : RestApiClient(baseUrl) {
    override fun setBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .addInterceptor(HostSelectionInterceptor())
            .addInterceptor(FlipperSettingImpl().getFlipperNetworkPlugin())
            .cache(ResponseCacheFactory().createCache(AppResources.getContext()))
    }
}