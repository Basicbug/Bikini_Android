/*
 * ViewModelsHelper.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui

import android.os.Bundle
import com.basicbug.core.ui.base.BaseActivity
import com.basicbug.core.ui.base.BaseViewModel

/**
 * @author MyeongKi
 */

interface ViewModelsHelper {
    fun getViewModels(owner: BaseActivity, savedInstanceState: Bundle?): List<BaseViewModel>
    fun saveState(viewModels: List<BaseViewModel>) {
        for (viewModel in viewModels) {
            viewModel.saveState()
        }
    }
}