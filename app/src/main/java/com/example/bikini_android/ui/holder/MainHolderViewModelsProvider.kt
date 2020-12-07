/*
 * MainHolderViewModelsProvider.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */


package com.example.bikini_android.ui.holder

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.bikini_android.ui.feeds.FeedsViewModel

/**
 * @author MyeongKi
 */

object MainHolderViewModelsProvider {
    @Suppress("UNCHECKED_CAST")
    fun getViewModels(owner: ViewModelStoreOwner): List<ViewModel> {
        return listOf(
            ViewModelProvider(
                owner
            )[FeedsViewModel::class.java]
        )
    }
}