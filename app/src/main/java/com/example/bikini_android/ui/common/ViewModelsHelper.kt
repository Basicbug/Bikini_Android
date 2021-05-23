/*
 * ViewModelsHelper.kt 2020. 12. 7
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common

import android.os.Bundle
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel

/**
 * @author MyeongKi
 */

interface ViewModelsHelper {
    fun getViewModels(owner: BaseActivity, savedInstanceState: Bundle?): List<BaseViewModel>
    fun saveState(viewModels: List<BaseViewModel>)
}
