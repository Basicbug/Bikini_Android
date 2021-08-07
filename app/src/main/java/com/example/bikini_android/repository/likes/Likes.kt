/*
 * Like.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author MyeongKi
 */
@Keep
@Parcelize
data class Likes(
    var targetId: String = "",
    var targetType: TargetType,
    var liked: Boolean = false
) : Parcelable {
    enum class TargetType(val type: String) {
        NONE("NONE"),
        FEED("FEED");
    }
}

fun String.convertLikesTargetType(): Likes.TargetType {
    Likes.TargetType.values().forEach {
        if (it.type == this) {
            return it
        }
    }
    return Likes.TargetType.NONE
}