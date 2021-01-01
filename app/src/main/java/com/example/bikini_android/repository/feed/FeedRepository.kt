/*
 * FeedRepository.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import io.reactivex.Single

/**
 * @author MyeongKi
 */

interface FeedRepository {
    fun getUserFeeds(
        userId: String
    ): Single<List<Feed>>
}