/*
 * MapViewModelFactoryProvider.kt 2021. 3. 17
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map.viewmodel

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.example.bikini_android.ui.board.BoardMapViewModel

/**
 * @author MyeongKi
 */
class BikiniMapViewModelFactoryProvider(
    owner: SavedStateRegistryOwner,
    savedInstanceState: Bundle?
) : AbstractSavedStateViewModelFactory(owner, savedInstanceState) {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T {
        return when {
            modelClass.isAssignableFrom(BikiniMapViewModel::class.java) -> {
                BikiniMapViewModel(handle) as T
            }
            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}