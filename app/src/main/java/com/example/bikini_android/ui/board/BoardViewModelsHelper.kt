/*
 * BoardViewModelsHelper.kt 2021. 9. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.board

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.basicbug.core.ui.ViewModelsHelper
import com.basicbug.core.ui.base.BaseActivity
import com.basicbug.core.ui.base.BaseViewModel

/**
 * @author MyeongKi
 */
object BoardViewModelsHelper : ViewModelsHelper {
    @Suppress("UNCHECKED_CAST")
    override fun getViewModels(
        owner: BaseActivity,
        savedInstanceState: Bundle?
    ): List<BaseViewModel> {
        return mutableListOf<BaseViewModel>().apply {
            add(
                ViewModelProvider(
                    owner,
                    BoardViewModelFactoryProvider(owner, savedInstanceState)
                )[BoardMapViewModel::class.java]
            )
            add(
                ViewModelProvider(
                    owner,
                    BoardViewModelFactoryProvider(owner, savedInstanceState)
                )[BoardViewModel::class.java]
            )
        }
    }
}