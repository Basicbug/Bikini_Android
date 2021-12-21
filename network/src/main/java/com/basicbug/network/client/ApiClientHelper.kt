/*
 * ApiClientHelper.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network.client

import retrofit2.Retrofit
import kotlin.reflect.KClass

/**
 * @author MyeongKi
 */

abstract class ApiClientHelper {
    protected abstract val mainClient: Retrofit
    protected abstract val invalidAuthClient: Retrofit

    fun <T : Any> createMainApiByService(clazz: KClass<T>): T {
        return mainClient.create(clazz.java)
    }

    fun <T : Any> createInvalidAuthApiByService(clazz: KClass<T>): T {
        return invalidAuthClient.create(clazz.java)
    }
}