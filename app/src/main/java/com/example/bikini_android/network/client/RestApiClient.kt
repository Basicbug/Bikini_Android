/*
 * RestApiClient.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.basicbug.core.app.AppResources
import com.example.bikini_android.network.HostSelectionInterceptor
import com.example.bikini_android.network.ResponseCacheFactory
import com.example.bikini_android.network.TokenAuthenticator
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
    override fun setBuilder(builder: OkHttpClient.Builder): OkHttpClient.Builder {
        return builder
            .connectTimeout(1, TimeUnit.MINUTES)
            .readTimeout(30, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .authenticator(TokenAuthenticator())
            .addInterceptor(HostSelectionInterceptor())
            .addInterceptor(FlipperSettingImpl().getFlipperNetworkPlugin())
            .cache(ResponseCacheFactory().createCache(AppResources.getContext()))
    }

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