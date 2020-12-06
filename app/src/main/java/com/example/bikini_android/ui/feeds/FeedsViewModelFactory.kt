/*
 * FeedsViewModelFactory.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.repository.feed.Feed

/**
 * @author MyeongKi
 */

class FeedsViewModelFactory(private val initFeeds: List<Feed>) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        @Suppress("UNCHECKED_CAST")
        return FeedsViewModel(initFeeds) as T
    }
}