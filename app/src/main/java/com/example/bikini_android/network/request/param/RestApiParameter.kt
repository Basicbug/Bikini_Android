/*
 * RestApiParameter.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.param

/**
 * @author MyeongKi
 */

abstract class RestApiParameter : HashMap<String, String>() {
    protected fun put(parameter: RestApiParameterKey, value: String) {
        if (value.isEmpty()) {
            remove(parameter.key)
        } else {
            put(parameter.key, value)
        }
    }

    protected fun get(parameter: RestApiParameterKey): String {
        return get(parameter.key) ?: ""
    }

    protected fun contains(parameter: RestApiParameterKey): Boolean {
        return contains(parameter.key)
    }

}