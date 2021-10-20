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
    private val mainClient: Retrofit = ApiFactory.getMainApiClient(NetworkConstants.DEV_URL)
    private val invalidAuthClient: Retrofit = ApiFactory.getInvalidApiClient(NetworkConstants.DEV_URL)

    @JvmStatic
    fun <T : Any> createMainApiByService(clazz: KClass<T>): T {
        return mainClient.create(clazz.java)
    }
    @JvmStatic
    fun <T : Any> createInvalidAuthApiByService(clazz: KClass<T>): T {
        return invalidAuthClient.create(clazz.java)
    }
}