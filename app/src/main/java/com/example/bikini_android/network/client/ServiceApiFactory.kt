/*
 * ServiceApiFactory.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.example.bikini_android.BuildConfig

/**
 * @author MyeongKi
 */

object ServiceApiFactory {
    fun getApiClient(baseUrl: String): RestApiClient {
        return if (BuildConfig.DEBUG) {
            BikiniDebugApiClient(baseUrl)
        } else {
            BikiniServiceApiClient(baseUrl)
        }
    }
}