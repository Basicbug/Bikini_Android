/*
 * BoardViewModelFactoryProvider.kt 2021. 9. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.board

import android.os.Bundle
import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner

/**
 * @author MyeongKi
 */
class BoardViewModelFactoryProvider(
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
            modelClass.isAssignableFrom(BoardMapViewModel::class.java) -> {
                BoardMapViewModel(handle) as T
            }
            modelClass.isAssignableFrom(BoardViewModel::class.java) -> {
                BoardViewModel(handle) as T
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}