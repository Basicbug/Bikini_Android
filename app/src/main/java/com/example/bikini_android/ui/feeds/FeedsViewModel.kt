/*
 * FeedsViewModel.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.lifecycle.ViewModel
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.map.FeedsLoadEvent
import com.example.bikini_android.util.bus.RxAction
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class FeedsViewModel(initFeeds: List<Feed>) : ViewModel() {
    private val _feeds: MutableList<Feed> = mutableListOf<Feed>().apply {
        this.addAll(initFeeds)
    }
    val feeds: List<Feed>
        get() = _feeds
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val disposables: CompositeDisposable = CompositeDisposable()

    fun loadFeedMarkers() {
        if (_feeds.isNotEmpty()) {
            itemEventRelay.accept(FeedsLoadEvent(_feeds))
        } else {
            loadFeedMarkersFromRepository()
        }
    }

    private fun loadFeedMarkersFromRepository() {
        loadTestFeedMarker().run {
            replaceFeeds(this)
            itemEventRelay.accept(FeedsLoadEvent(this))
        }

    }

    private fun loadTestFeedMarker(): List<Feed> {
        return mutableListOf<Feed>().apply {
            add(
                Feed(
                    1,
                    "1",
                    "test",
                    "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
                    "https://homepages.cae.wisc.edu/~ece533/images/airplane.png",
                    LatLng(37.363188, 127.107497),
                    1
                )
            )
            add(
                Feed(
                    1,
                    "2",
                    "test",
                    "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                    "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png",
                    LatLng(37.362424, 127.106644),
                    1
                )
            )
            add(
                Feed(
                    1,
                    "3",
                    "test",
                    "https://homepages.cae.wisc.edu/~ece533/images/baboon.png",
                    "https://homepages.cae.wisc.edu/~ece533/images/baboon.png",
                    LatLng(37.361290, 127.107213),
                    1

                )
            )
            add(
                Feed(
                    1,
                    "4",
                    "test",
                    "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
                    "https://homepages.cae.wisc.edu/~ece533/images/boat.png",
                    LatLng(37.361025, 127.107824),
                    1
                )
            )
        }
    }

    private fun replaceFeeds(feeds: List<Feed>) {
        _feeds.clear()
        _feeds.addAll(feeds)
    }

    override fun onCleared() {
        _feeds.clear()
        disposables.dispose()
        super.onCleared()
    }
}