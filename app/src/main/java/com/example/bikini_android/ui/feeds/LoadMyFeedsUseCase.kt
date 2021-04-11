/*
 * InvokeMyFeedsUseCase.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.repository.feed.Feed
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

class LoadMyFeedsUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>
) : LoadFeedsUseCase {
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()
    private val testMyId = "ChoMk"
    override fun execute(lastFeedsRendered: List<Feed>) {
        if (lastFeedsRendered.isNotEmpty()) {
            itemEventRelay.accept(FeedsEvent(lastFeedsRendered, FeedsType.MY_FEEDS))
        } else {
            feedsRepository
                .getUserFeedsFromRemote(testMyId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { result ->
                    result?.let {
                        itemEventRelay.accept(FeedsEvent(it, FeedsType.MY_FEEDS))
                    }
                }
                .addTo(disposable)
        }
    }
}