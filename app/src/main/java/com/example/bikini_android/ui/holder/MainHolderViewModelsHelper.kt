/*
 * MainHolderViewModelsProvider.kt 2020. 12. 6
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */


package com.example.bikini_android.ui.holder

import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.feeds.FeedsViewModel

/**
 * @author MyeongKi
 */

object MainHolderViewModelsHelper {
    @Suppress("UNCHECKED_CAST")
    fun getViewModels(owner: ViewModelStoreOwner): List<BaseViewModel> {
        return listOf(
            ViewModelProvider(
                owner
            )[FeedsViewModel::class.java]
        )
    }
    fun saveState(viewModels:List<BaseViewModel>){
        for(viewModel in viewModels){
            viewModel.saveState()
        }
    }
}