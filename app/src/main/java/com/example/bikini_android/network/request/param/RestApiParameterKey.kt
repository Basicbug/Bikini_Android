/*
 * RestApiParameterKey.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.param

/**
 * @author MyeongKi
 */

enum class RestApiParameterKey(val key: String) {
    LATITUDE("latitude"),
    LONGITUDE("longitude"),
    RADIUS("radius"),
    TARGET_ID("targetId"),
    TARGET_TYPE("targetType"),
}