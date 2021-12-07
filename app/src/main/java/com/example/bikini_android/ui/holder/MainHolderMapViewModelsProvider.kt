/*
 * MapViewModelsProvider.kt 2021. 3. 17
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.basicbug.core.ui.base.BaseActivity
import com.basicbug.core.ui.base.BaseViewModel
import com.example.bikini_android.ui.map.viewmodel.BikiniMapViewModel
import com.example.bikini_android.ui.map.viewmodel.BikiniMapViewModelFactoryProvider

/**
 * @author MyeongKi
 */
object MainHolderMapViewModelsProvider {
    fun getMapViewModels(
        owner: BaseActivity,
        savedInstanceState: Bundle?
    ): List<BaseViewModel> {
        return listOf(
            ViewModelProvider(
                owner,
                BikiniMapViewModelFactoryProvider(owner, savedInstanceState)
            )[BikiniMapViewModel::class.java]
        )
    }
}