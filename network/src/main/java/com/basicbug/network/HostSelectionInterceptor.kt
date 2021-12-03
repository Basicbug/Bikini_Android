/*
 * HostSelectionInterceptor.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network

import com.basicbug.core.ui.settings.DeveloperSetting
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author qwebnm7788
 */
class HostSelectionInterceptor(private val developerSetting: DeveloperSetting) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()
        val host = developerSetting.getBaseDomain().toHttpUrlOrNull()

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