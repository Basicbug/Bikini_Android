/*
 * FeedsViewModel.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.map.FeedsLoadEvent
import com.example.bikini_android.util.bus.RxAction
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class FeedsViewModel(private val handle: SavedStateHandle) : BaseViewModel() {
    private var _feeds: List<Feed> = handle.get<MutableList<Feed>>(KEY_FEEDS) ?: mutableListOf()

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
            _feeds = this
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

    override fun saveState() {
        handle[KEY_FEEDS] = _feeds
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    companion object {
        private const val KEY_FEEDS = "keyFeeds"
    }
}