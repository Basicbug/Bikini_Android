/*
 * LikeResponse.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.response

import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.repository.likes.convertLikesTargetType
import com.google.gson.annotations.SerializedName

/**
 * @author MyeongKi
 */
class LikesResponse : JsonResponseWrapper<LikesResponse.Result>() {
    data class Result(
        @SerializedName("liked")
        val liked: Boolean,
        @SerializedName("targetId")
        val targetId: String,
        @SerializedName("targetType")
        val targetType: String
    )
}

fun LikesResponse.Result.convertLikes(): Likes {
    return Likes(targetId, targetType.convertLikesTargetType(), liked)
}