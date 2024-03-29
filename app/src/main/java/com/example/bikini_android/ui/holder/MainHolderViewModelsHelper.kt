/*
 * MainHolderViewModelsProvider.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import com.basicbug.core.ui.ViewModelsHelper
import com.basicbug.core.ui.base.BaseActivity
import com.basicbug.core.ui.base.BaseViewModel

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
            addAll(MainHolderFeedsViewModelsProvider.getFeedsViewModels(owner, savedInstanceState))
            addAll(MainHolderMapViewModelsProvider.getMapViewModels(owner, savedInstanceState))
        }
    }
}