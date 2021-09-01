/*
 * LikesParameter.kt 2021. 8. 21
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.param

import com.example.bikini_android.repository.likes.LikesTargetType

/**
 * @author MyeongKi
 */
class LikesParameter : RestApiParameter() {
    fun setLikesParameter(targetId: String, @LikesTargetType targetType: String) {
        put(RestApiParameterKey.TARGET_ID, targetId)
        put(RestApiParameterKey.TARGET_TYPE, targetType)
    }
}