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
import com.basicbug.core.rx.DefaultSchedulerProvider
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.util.file.FileUtilsImpl
import com.example.bikini_android.util.map.LocationUtils

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
                BoardMapViewModel(handle, LocationUtils) as T
            }
            modelClass.isAssignableFrom(BoardViewModel::class.java) -> {
                BoardViewModel(
                    handle,
                    FeedRepositoryInjector.getFeedRepository(),
                    LoginManagerProxy,
                    FileUtilsImpl,
                    DefaultSchedulerProvider()
                ) as T
            }

            else -> {
                throw IllegalArgumentException()
            }
        }
    }
}