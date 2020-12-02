/*
 * BikiniMapViewModel.kt 2020. 12. 2
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import android.content.Context
import android.util.ArrayMap
import androidx.lifecycle.ViewModel
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.util.bus.RxAction
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class BikiniMapFeedsViewModel : ViewModel() {
    private val _feeds: MutableList<Feed> = mutableListOf()
    private var itemEventRelayTable: ArrayMap<Context, Relay<RxAction>> = ArrayMap()

    fun addEventRelay(context: Context, itemEventRelay: Relay<RxAction>) {
        itemEventRelayTable[context] = itemEventRelay
    }

    fun deleteEventRelay(context: Context) {
        itemEventRelayTable[context] = null
    }

    fun loadFeedMarkers(disposables: CompositeDisposable) {
        if (_feeds.isNotEmpty()) {
            invokeCompletedLoadFeedEvent()
        } else {
            loadFeedMarkersFromRepository(disposables)
        }
    }

    private fun loadFeedMarkersFromRepository(disposables: CompositeDisposable) {
        _feeds.clear()
        _feeds.addAll(loadTestFeedMarker())
        invokeCompletedLoadFeedEvent()
    }

    private fun loadTestFeedMarker(): List<Feed> {
        return mutableListOf<Feed>().apply {
            add(
                Feed(
                    1,
                    "1",
                    "test",
                    LatLng(37.363188, 127.107497),
                    "https://homepages.cae.wisc.edu/~ece533/images/airplane.png"
                )
            )
            add(
                Feed(
                    1,
                    "2",
                    "test",
                    LatLng(37.362424, 127.106644),
                    "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png"
                )
            )
            add(
                Feed(
                    1,
                    "3",
                    "test",
                    LatLng(37.361290, 127.107213),
                    "https://homepages.cae.wisc.edu/~ece533/images/baboon.png"
                )
            )
            add(
                Feed(
                    1,
                    "4",
                    "test",
                    LatLng(37.361025, 127.107824),
                    "https://homepages.cae.wisc.edu/~ece533/images/boat.png"
                )
            )
        }
    }

    private fun invokeCompletedLoadFeedEvent() {
        for (itemEventRelay in itemEventRelayTable.values) {
            itemEventRelay.accept(FeedsLoadEvent(_feeds))
        }
    }

    override fun onCleared() {
        _feeds.clear()
        itemEventRelayTable.clear()
        super.onCleared()
    }
}