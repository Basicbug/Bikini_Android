/*
 * ApiClientHelper.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.example.bikini_android.network.NetworkConstants
import retrofit2.Retrofit
import kotlin.reflect.KClass

/**
 * @author MyeongKi
 */

object ApiClientHelper {
    private val apiClient: Retrofit = ServiceApiFactory.getApiClient(NetworkConstants.BASE_URL)

    @JvmStatic
    fun <T : Any> createApiByService(clazz: KClass<T>): T {
        return apiClient.create(clazz.java)
    }
}