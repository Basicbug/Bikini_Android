/*
 * FeedRepositoryInjector.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

/**
 * @author MyeongKi
 */

object FeedRepositoryInjector {
    fun getFeedRepository(): FeedRepository {
        return FeedRepositoryImpl.getInstance()
    }
}