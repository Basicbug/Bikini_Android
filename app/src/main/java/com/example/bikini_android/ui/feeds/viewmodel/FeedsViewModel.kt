/*
 * FeedsViewModel.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds.viewmodel

import androidx.lifecycle.SavedStateHandle
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.base.BaseViewModel
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.repository.likes.LikesTargetType
import com.example.bikini_android.ui.feeds.FeedsEvent
import com.example.bikini_android.ui.feeds.FeedsType
import com.example.bikini_android.ui.feeds.LoadFeedsUseCase
import com.example.bikini_android.ui.likes.LikesViewModel
import com.google.android.gms.maps.model.LatLng
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
    private var _feedsRendered: List<Feed> =
        handle.get<MutableList<Feed>>(KEY_FEEDS_RENDERED) ?: mutableListOf()
    val feedsRendered: List<Feed>
        get() = _feedsRendered

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    protected val disposables: CompositeDisposable = CompositeDisposable()
    open lateinit var loadFeedsUseCase: LoadFeedsUseCase
    val likeViewModel = LikesViewModel(disposables, itemEventRelay, LikesTargetType.FEED)

    init {
        itemEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .filter { it.feedsType == feedType }
            .subscribe { event ->
                _feedsRendered = event.feeds
            }.addTo(disposables)
    }

    fun loadFeeds(feeds: List<Feed>? = null) {
        feeds?.let {
            _feedsRendered = feeds
        }
        loadFeedsUseCase.execute(lastFeedsRendered = feedsRendered)
    }

    fun loadFeeds(latLng: LatLng, radius: Float) {
        loadFeedsUseCase.execute(latLng, radius)
    }

    fun reloadFeeds() {
        loadFeedsUseCase.execute()
    }

    override fun saveState() {
        handle[KEY_FEEDS_RENDERED] = feedsRendered
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    companion object {
        private const val KEY_FEEDS_RENDERED = "keyFeedsRendered"
    }
}
