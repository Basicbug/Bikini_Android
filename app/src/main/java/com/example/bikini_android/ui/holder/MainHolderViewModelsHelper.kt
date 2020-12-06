/*
 * MainHolderViewModelsProvider.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */


package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.feeds.FeedsViewModel
import com.example.bikini_android.ui.feeds.FeedsViewModelFactory

/**
 * @author MyeongKi
 */

object MainHolderViewModelsHelper {
    private const val KEY_FEEDS = "keyFeeds"

    @Suppress("UNCHECKED_CAST")
    fun getViewModels(owner: ViewModelStoreOwner, outState: Bundle?): List<ViewModel> {
        return listOf(
            ViewModelProvider(
                owner,
                FeedsViewModelFactory(outState?.getParcelableArrayList<Feed>(KEY_FEEDS) as List<Feed>? ?: listOf())
            )[FeedsViewModel::class.java]
        )
    }

    fun saveInstanceState(outState: Bundle, viewModels: List<ViewModel>) {
        for (viewModel in viewModels) {
            when (viewModel) {
                is FeedsViewModel -> {
                    saveFeedsSate(outState, viewModel)
                }
            }
        }
    }

    private fun saveFeedsSate(outState: Bundle, viewModel: FeedsViewModel) {
        outState.putParcelableArrayList(KEY_FEEDS, viewModel.feeds as ArrayList<Feed>)
    }
}