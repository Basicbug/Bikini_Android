/*
 * FeedsViewModelsProvider.kt 2021. 3. 17
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.feeds.viewmodel.AllFeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.FeedsViewModelFactoryProvider
import com.example.bikini_android.ui.feeds.viewmodel.MyFeedsViewModel
import com.example.bikini_android.ui.feeds.viewmodel.RankingFeedsViewModel

/**
 * @author MyeongKi
 */
object FeedsViewModelsProvider {
    fun getFeedsViewModels(
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
}