/*
 * LikeItRepositoryInjector.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

import java.lang.IllegalArgumentException

/**
 * @author MyeongKi
 */
object LikesRepositoryInjector {
    fun getLikesRepository(targetType: Likes.TargetType): LikesRepository {
        when(targetType){
            Likes.TargetType.FEED->{
                return LikesFeedRepository.getInstance()
            }
            else->{
                throw IllegalArgumentException("invalid target type type")
            }
        }

    }
}