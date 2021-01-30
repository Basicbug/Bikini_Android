/*
 * MainHolderViewModelsProvider.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.common.ViewModelsHelper
import com.example.bikini_android.ui.feeds.viewmodel.AllFeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModelFactoryProvider
import com.example.bikini_android.ui.feeds.viewmodel.MyFeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.RankingFeedsViewModel

/**
 * @author MyeongKi
 */

object MainHolderViewModelsHelper : ViewModelsHelper {
    @Suppress("UNCHECKED_CAST")
    override fun getViewModels(
        owner: BaseActivity,
        savedInstanceState: Bundle?
    ): List<BaseViewModel> {
        return listOf(
            ViewModelProvider(
                owner,
                FeedsViewModelFactoryProvider(owner, savedInstanceState)
            )[RankingFeedsViewModel::class.java],
            ViewModelProvider(
                owner,
                FeedsViewModelFactoryProvider(owner, savedInstanceState)
            )[AllFeedsViewModel::class.java],
            ViewModelProvider(
                owner,
                FeedsViewModelFactoryProvider(owner, savedInstanceState)
            )[MyFeedsViewModel::class.java]
        )
    }

    override fun saveState(viewModels: List<BaseViewModel>) {
        for (viewModel in viewModels) {
            viewModel.saveState()
        }
    }
}