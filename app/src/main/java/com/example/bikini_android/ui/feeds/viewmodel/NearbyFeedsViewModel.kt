/*
 * NearFeedsViewModel.kt 2021. 2. 6
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.LoadFeedsUseCase
import com.example.bikini_android.ui.feeds.LoadNearbyFeedsUseCase

/**
 * @author MyeongKi
 */
class NearbyFeedsViewModel(handle: SavedStateHandle) : FeedsViewModel(handle, FeedsType.NEARBY_FEEDS) {
    override var loadFeedsUseCase: LoadFeedsUseCase =
        LoadNearbyFeedsUseCase(disposables, itemEventRelay)
}