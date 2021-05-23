/*
 * MainHolderViewModelsProvider.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.common.ViewModelsHelper

/**
 * @author MyeongKi
 */

object MainHolderViewModelsHelper : ViewModelsHelper {
    @Suppress("UNCHECKED_CAST")
    override fun getViewModels(
        owner: BaseActivity,
        savedInstanceState: Bundle?
    ): List<BaseViewModel> {
        return mutableListOf<BaseViewModel>().apply {
            addAll(FeedsViewModelsProvider.getFeedsViewModels(owner, savedInstanceState))
            addAll(MapViewModelsProvider.getMapViewModels(owner, savedInstanceState))
        }
    }

    override fun saveState(viewModels: List<BaseViewModel>) {
        for (viewModel in viewModels) {
            viewModel.saveState()
        }
    }
}
