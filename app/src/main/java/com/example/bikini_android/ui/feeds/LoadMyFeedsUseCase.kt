/*
 * InvokeMyFeedsUseCase.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.app.TEST_USER_ID
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.sort.SortFeedCriteriaProvider
import com.example.bikini_android.sort.SortFeedExecutor
import com.example.bikini_android.sort.SortTarget
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class LoadMyFeedsUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>
) : LoadFeedsUseCase {
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()

    override fun execute(lastFeedsRendered: List<Feed>) {
        if (lastFeedsRendered.isNotEmpty()) {
            itemEventRelay.accept(FeedsEvent(lastFeedsRendered, FeedsType.MY_FEEDS))
        } else {
            execute()
        }
    }

    override fun execute() {
        feedsRepository
            .getUserFeedsFromRemote(TEST_USER_ID)
            .subscribe { result ->
                result?.let {
                    itemEventRelay.accept(
                        FeedsEvent(
                            SortFeedExecutor.execute(
                                it,
                                SortFeedCriteriaProvider.getCriteria(SortTarget.FEED_UPDATE)
                            ), FeedsType.MY_FEEDS
                        )
                    )
                }
            }
            .addTo(disposable)
    }
}