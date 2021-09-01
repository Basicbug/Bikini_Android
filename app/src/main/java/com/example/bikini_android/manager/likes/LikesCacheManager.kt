/*
 * LikeCacheManager.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.manager.likes

import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.repository.likes.LikesTargetType

/**
 * @author MyeongKi
 */
object LikesCacheManager {
    private val likesCacheTable by lazy { HashMap<Pair<String, @LikesTargetType String>, Likes>() }

    fun cacheLikes(like: Likes) {
        likesCacheTable[Pair(like.targetId, like.targetType)] = like
    }

    fun isLikes(targetId: String, @LikesTargetType targetType: String): Likes? {
        return likesCacheTable[Pair(targetId, targetType)]
    }
}