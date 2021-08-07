/*
 * LikesParameter.kt 2021. 8. 21
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.param

import com.example.bikini_android.repository.likes.Likes

/**
 * @author MyeongKi
 */
class LikesParameter : RestApiParameter() {
    fun setLikesParameter(targetId: String, targetType: Likes.TargetType) {
        put(RestApiParameterKey.TARGET_ID, targetId)
        put(RestApiParameterKey.TARGET_TYPE, targetType.type)
    }
}