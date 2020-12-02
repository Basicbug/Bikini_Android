/*
 * BikiniMapViewModel.kt 2020. 12. 2
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import androidx.lifecycle.ViewModel
import com.example.bikini_android.repository.feed.FeedMarker
import com.example.bikini_android.util.bus.RxAction
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class BikiniMapViewModel : ViewModel() {
    private val _feedMarkers: MutableList<FeedMarker> = mutableListOf()
    private lateinit var itemEventRelay: Relay<RxAction>
    private lateinit var disposables: CompositeDisposable

    fun init(itemEventRelay: Relay<RxAction>, disposables: CompositeDisposable) {
        this.itemEventRelay = itemEventRelay
        this.disposables = disposables
    }

    fun loadFeedMarkers() {
        if (_feedMarkers.isNotEmpty()) {
            itemEventRelay.accept(FeedMarkersLoadEvent(_feedMarkers))
        } else {
            loadFeedMarkersFromRepository()
        }
    }

    private fun loadFeedMarkersFromRepository(){
        itemEventRelay.accept(FeedMarkersLoadEvent(loadTestFeedMarker()))
    }


    private fun loadTestFeedMarker(): List<FeedMarker> {
        return mutableListOf<FeedMarker>().apply {
            add(
                FeedMarker(
                    "1",
                    LatLng(37.363188, 127.107497),
                    "https://homepages.cae.wisc.edu/~ece533/images/airplane.png"
                )
            )
            add(
                FeedMarker(
                    "2",
                    LatLng(37.362424, 127.106644),
                    "https://homepages.cae.wisc.edu/~ece533/images/arctichare.png"
                )
            )
            add(
                FeedMarker(
                    "3",
                    LatLng(37.361290, 127.107213),
                    "https://homepages.cae.wisc.edu/~ece533/images/baboon.png"
                )
            )
            add(
                FeedMarker(
                    "4",
                    LatLng(37.361025, 127.107824),
                    "https://homepages.cae.wisc.edu/~ece533/images/boat.png"
                )
            )
        }
    }

    override fun onCleared() {
        _feedMarkers.clear()
        super.onCleared()
    }
}