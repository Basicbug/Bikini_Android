/*
 * SampleParameter.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.param

/**
 * @author MyeongKi
 */

class SampleParameter : RestApiParameter() {
    fun setValue(value: String) {
        put(RestApiParameterKey.SAMPLE, value)
    }

    fun getValue(): String {
        return get(RestApiParameterKey.SAMPLE)
    }
}