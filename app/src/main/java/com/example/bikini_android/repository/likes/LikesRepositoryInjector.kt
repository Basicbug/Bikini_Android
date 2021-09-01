/*
 * LikeItRepositoryInjector.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

/**
 * @author MyeongKi
 */
object LikesRepositoryInjector {
    fun getLikesRepository(@LikesTargetType targetType: String): LikesRepository {
        when (targetType) {
            LikesTargetType.FEED -> {
                return LikesFeedRepository.getInstance()
            }
            else -> {
                throw IllegalArgumentException("invalid target type type")
            }
        }
    }
}