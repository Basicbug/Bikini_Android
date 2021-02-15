/*
 * AllFeedsViewModel.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.LoadAllFeedsUseCase
import com.example.bikini_android.ui.feeds.LoadFeedsUseCase

/**
 * @author MyeongKi
 */
class AllFeedsViewModel(handle: SavedStateHandle) : FeedsViewModel(handle, FeedsType.ALL_FEEDS) {
    override var loadFeedsUseCase: LoadFeedsUseCase =
        LoadAllFeedsUseCase(disposables, itemEventRelay)
}