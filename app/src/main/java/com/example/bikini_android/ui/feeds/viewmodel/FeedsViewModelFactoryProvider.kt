/*
 * FeedsViewModelFactoryProvider.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.bikini_android.ui.feeds.FeedsType

/**
 * @author MyeongKi
 */

class FeedsViewModelFactoryProvider(
    owner: SavedStateRegistryOwner,
    savedInstanceState: Bundle?
) : AbstractSavedStateViewModelFactory(owner, savedInstanceState) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return when {
            modelClass.isAssignableFrom(RankingFeedsViewModel::class.java) -> {
                RankingFeedsViewModel(handle) as T
            }
            modelClass.isAssignableFrom(MyFeedsViewModel::class.java) -> {
                MyFeedsViewModel(handle) as T
            }
            modelClass.isAssignableFrom(AllFeedsViewModel::class.java) -> {
                AllFeedsViewModel(handle) as T
            }
            modelClass.isAssignableFrom(NearbyFeedsViewModel::class.java) ->{
                NearbyFeedsViewModel(handle) as T
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }

    companion object {
        @Suppress("UNCHECKED_CAST")
        fun getFeedViewModelClazz(feedsType: FeedsType): Class<FeedsViewModel> {
            return when (feedsType) {
                FeedsType.MY_FEEDS -> {
                    MyFeedsViewModel::class.java as Class<FeedsViewModel>
                }
                FeedsType.RANKING_FEEDS -> {
                    RankingFeedsViewModel::class.java as Class<FeedsViewModel>
                }
                FeedsType.NEARBY_FEEDS -> {
                    NearbyFeedsViewModel::class.java as Class<FeedsViewModel>
                }
                FeedsType.ALL_FEEDS -> {
                    AllFeedsViewModel::class.java as Class<FeedsViewModel>
                }
            }
        }
    }
}