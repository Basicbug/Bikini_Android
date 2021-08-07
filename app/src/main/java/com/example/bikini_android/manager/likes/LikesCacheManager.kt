/*
 * LikeCacheManager.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.manager.likes

import com.example.bikini_android.repository.likes.Likes

/**
 * @author MyeongKi
 */
object LikesCacheManager {
    private val likesCacheTable by lazy { HashMap<Pair<String, Likes.TargetType>, Likes>() }

    fun cacheLikes(like: Likes) {
        likesCacheTable[Pair(like.targetId, like.targetType)] = like
    }

    fun isLikes(targetId: String, targetType: Likes.TargetType): Likes? {
        return likesCacheTable[Pair(targetId, targetType)]
    }
}