/*
 * LikeRepository.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

import io.reactivex.Single

/**
 * @author MyeongKi
 */
interface LikesRepository {
    fun addLikes(targetId: String): Single<Likes?>
    fun removeLikes(targetId: String): Single<Likes?>
    fun checkLikes(targetId: String): Single<Likes?>
}