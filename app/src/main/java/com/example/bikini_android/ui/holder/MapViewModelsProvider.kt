/*
 * MapViewModelsProvider.kt 2021. 3. 17
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.map.viewmodel.BikiniMapViewModel
import com.example.bikini_android.ui.map.viewmodel.MapViewModelFactoryProvider

/**
 * @author MyeongKi
 */
object MapViewModelsProvider {
    fun getMapViewModels(
        owner: BaseActivity,
        savedInstanceState: Bundle?
    ): List<BaseViewModel> {
        return listOf(
            ViewModelProvider(
                owner,
                MapViewModelFactoryProvider(owner, savedInstanceState)
            )[BikiniMapViewModel::class.java]
        )
    }
}