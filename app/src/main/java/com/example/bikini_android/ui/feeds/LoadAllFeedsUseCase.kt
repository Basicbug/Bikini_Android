/*
 * LoadAllFeedsUseCase.kt 2021. 1. 5
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import android.util.Log
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.ui.map.FeedsEvent
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author MyeongKi
 */
class LoadAllFeedsUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>
) {
    
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()

    fun execute() {
        feedsRepository
            .getAllFeedsFromRemote()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                itemEventRelay.accept(FeedsEvent(it, FeedsType.ALL_FEEDS))
            }, {
                Log.d("tset", it.toString())
            })
            .addTo(disposable)
    }
}