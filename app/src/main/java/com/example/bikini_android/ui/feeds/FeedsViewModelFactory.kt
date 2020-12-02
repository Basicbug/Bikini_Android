/*
 * FeedsViewModelFactory.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

@Suppress("UNCHECKED_CAST")
class FeedsViewModelFactory(
    private val itemEventRelay: Relay<RxAction>,
    private val disposables: CompositeDisposable
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(FeedsViewModel::class.java)) {
            FeedsViewModel(itemEventRelay, disposables) as T
        } else {
            throw IllegalArgumentException()
        }
    }

}