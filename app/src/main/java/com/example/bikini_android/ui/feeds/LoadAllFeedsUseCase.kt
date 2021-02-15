/*
 * LoadAllFeedsUseCase.kt 2021. 1. 5
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.ui.map.FeedsEvent
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.logging.Logger
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
) : LoadFeedsUseCase {
    private val logger = Logger().apply {
        TAG = this@LoadAllFeedsUseCase.javaClass.simpleName
    }
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()

    override fun execute(lastFeedsRendered: List<Feed>) {
        if (lastFeedsRendered.isNotEmpty()) {
            itemEventRelay.accept(FeedsEvent(lastFeedsRendered, FeedsType.ALL_FEEDS))
        } else {
            feedsRepository
                .getAllFeedsFromRemote()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    itemEventRelay.accept(FeedsEvent(it, FeedsType.ALL_FEEDS))
                }, {
                    logger.error { it.toString() }
                })
                .addTo(disposable)
        }
    }
}