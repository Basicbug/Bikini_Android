/*
 * HostSelectionInterceptor.kt 2021. 2. 11
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 */

package com.example.bikini_android.network

import com.example.bikini_android.ui.settings.DeveloperSettingImpl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author qwebnm7788
 */
class HostSelectionInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val host = DeveloperSettingImpl.getBaseDomain().toHttpUrlOrNull()

        if (host != null) {
            val newUrl = request.url.newBuilder()
                .scheme(host.scheme)
                .host(host.host)
                .port(host.port)
                .build()

            request = request.newBuilder().url(newUrl).build()
        }

        return chain.proceed(request)
    }
}
