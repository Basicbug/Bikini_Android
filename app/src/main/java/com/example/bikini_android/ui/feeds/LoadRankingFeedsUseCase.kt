/*
 * LoadRankFeedsUseCase.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.basicbug.core.rx.addTo
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.feed.FeedRepositoryInjector
import com.example.bikini_android.sort.SortFeedCriteriaProvider
import com.example.bikini_android.sort.SortFeedExecutor
import com.example.bikini_android.sort.SortTarget
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class LoadRankingFeedsUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>
) : LoadFeedsUseCase {
    private val feedsRepository = FeedRepositoryInjector.getFeedRepository()

    override fun execute(lastFeedsRendered: List<Feed>) {
        if (lastFeedsRendered.isNotEmpty()) {
            itemEventRelay.accept(FeedsEvent(lastFeedsRendered, FeedsType.RANKING_FEEDS))
        } else {
            execute()
        }
    }

    override fun execute() {
        feedsRepository
            .getRankingFeedsFromRemote(LIMIT)
            .subscribe { result ->
                result?.let {
                    itemEventRelay.accept(
                        FeedsEvent(
                            SortFeedExecutor.execute(
                                it,
                                SortFeedCriteriaProvider.getCriteria(SortTarget.FEED_DISTANCE)
                            ), FeedsType.RANKING_FEEDS
                        )
                    )
                }
            }
            .addTo(disposable)
    }

    companion object {
        private const val LIMIT = 10
    }
}