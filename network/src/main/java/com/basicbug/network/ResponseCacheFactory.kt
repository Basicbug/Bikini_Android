/*
 * ResponseCacheFactory.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network

import android.content.Context
import okhttp3.Cache

/**
 * @author MyeongKi
 */
class ResponseCacheFactory {
    fun createCache(context: Context): Cache = Cache(context.cacheDir, CACHE_SIZE_BYTES)

    companion object {
        private const val CACHE_SIZE_BYTES = 10 * 1024 * 1024L
    }
}