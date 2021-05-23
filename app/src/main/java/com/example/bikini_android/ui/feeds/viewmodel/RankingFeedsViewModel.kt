/*
 * RankingFeedsViewModel.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.LoadFeedsUseCase
import com.example.bikini_android.ui.feeds.LoadRankingFeedsUseCase

/**
 * @author MyeongKi
 */
class RankingFeedsViewModel(handle: SavedStateHandle) :
    FeedsViewModel(handle, FeedsType.RANKING_FEEDS) {
    override var loadFeedsUseCase: LoadFeedsUseCase =
        LoadRankingFeedsUseCase(disposables, itemEventRelay)
}
