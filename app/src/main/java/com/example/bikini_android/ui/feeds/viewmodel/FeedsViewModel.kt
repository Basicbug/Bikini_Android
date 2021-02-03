/*
 * FeedsViewModel.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.LoadFeedsUseCase
import com.example.bikini_android.ui.map.FeedsEvent
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

abstract class FeedsViewModel(
    private val handle: SavedStateHandle,
    private val feedType: FeedsType
) : BaseViewModel() {
    private var _feeds: List<Feed> =
        handle.get<MutableList<Feed>>(KEY_FEEDS) ?: mutableListOf()
    val feeds: List<Feed>
        get() = _feeds

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    protected val disposables: CompositeDisposable = CompositeDisposable()
    open lateinit var loadFeedsUseCase: LoadFeedsUseCase

    init {
        itemEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.feedsType == feedType }
            .subscribe { event ->
                _feeds = event.feeds
            }.addTo(disposables)
    }

    fun initFeeds() {
        if (feeds.isNotEmpty()) {
            itemEventRelay.accept(FeedsEvent(feeds, feedType))
        } else {
            loadFeedsUseCase.execute()
        }
    }

    fun refreshMyFeeds() {
        loadFeedsUseCase.execute()
    }

    override fun saveState() {
        handle[KEY_FEEDS] = feeds
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    companion object {
        private const val KEY_FEEDS = "keyFeeds"
    }
}
