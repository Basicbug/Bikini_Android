package com.example.bikini_android.ui.settings

import okhttp3.Interceptor
import okhttp3.Response

/**
 * @author bsgreentea
 */
class FlipperSettingImpl {

    fun getFlipperNetworkPlugin() = object : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            return chain.proceed(chain.request())
        }
    }
}
