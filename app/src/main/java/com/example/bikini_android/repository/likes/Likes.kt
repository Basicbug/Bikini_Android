/*
 * Like.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

import android.os.Parcelable
import androidx.annotation.Keep
import androidx.annotation.StringDef
import kotlinx.parcelize.Parcelize

/**
 * @author MyeongKi
 */
@Keep
@Parcelize
data class Likes(
    var targetId: String = "",
    @LikesTargetType
    var targetType: String,
    var liked: Boolean = false
) : Parcelable

@StringDef(
    LikesTargetType.FEED,
    LikesTargetType.NONE
)
annotation class LikesTargetType {
    companion object {
        const val FEED = "FEED"
        const val NONE = "NONE"
    }
}