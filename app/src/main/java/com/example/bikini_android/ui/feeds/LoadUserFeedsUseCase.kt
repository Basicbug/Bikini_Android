/*
 * LoadUserFeedsUseCase.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.repository.feed.FeedRepositoryInjector

/**
 * @author MyeongKi
 */

class LoadUserFeedsUseCase {
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()
}