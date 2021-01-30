/*
 * MyFeedsViewModel.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.LoadFeedsUseCase
import com.example.bikini_android.ui.feeds.LoadMyFeedsUseCase

/**
 * @author MyeongKi
 */
class MyFeedsViewModel(handle: SavedStateHandle) : FeedsViewModel(handle, FeedsType.MY_FEEDS) {
    override var loadFeedsUseCase: LoadFeedsUseCase =
        LoadMyFeedsUseCase(disposables, itemEventRelay)
}