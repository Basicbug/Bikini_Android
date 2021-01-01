/*
 * FeedsViewModel.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import androidx.lifecycle.SavedStateHandle
import com.example.bikini_android.repository.feed.Feed
import com.example.bikini_android.ui.base.BaseViewModel
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

class FeedsViewModel(private val handle: SavedStateHandle) : BaseViewModel() {
    private var _myFeeds: List<Feed> = handle.get<MutableList<Feed>>(KEY_MY_FEEDS) ?: mutableListOf()

    val myFeeds: List<Feed>
        get() = _myFeeds
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val disposables: CompositeDisposable = CompositeDisposable()

    private val loadMyFeedsUseCase = LoadMyFeedsUseCase(disposables, itemEventRelay)
    init {
        itemEventRelay
            .ofType(FeedsEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                when (event.feedsType){
                    FeedsType.MY_FEEDS -> {
                        _myFeeds = event.feeds
                    }
                    FeedsType.NEAR_LOCATION_FEEDS -> {

                    }
                    FeedsType.HOT_RANKING_FEEDS -> {

                    }
                }
            }.addTo(disposables)
    }
    fun initFeeds(feedsType: FeedsType) {
        when (feedsType) {
            FeedsType.MY_FEEDS -> {
                if (_myFeeds.isNotEmpty()) {
                    itemEventRelay.accept(FeedsEvent(_myFeeds, FeedsType.MY_FEEDS))
                } else {
                    loadMyFeeds()
                }
            }
            FeedsType.NEAR_LOCATION_FEEDS -> {
                if (_myFeeds.isNotEmpty()) {
                    itemEventRelay.accept(FeedsEvent(_myFeeds, FeedsType.MY_FEEDS))
                } else {
                    loadMyFeeds()
                }
            }
            FeedsType.HOT_RANKING_FEEDS -> {
                if (_myFeeds.isNotEmpty()) {
                    itemEventRelay.accept(FeedsEvent(_myFeeds, FeedsType.MY_FEEDS))
                } else {
                    loadMyFeeds()
                }
            }
        }
    }

    fun refreshMyFeeds(feedsType: FeedsType) {
        when (feedsType) {
            FeedsType.MY_FEEDS -> {
                loadMyFeeds()
            }
            FeedsType.NEAR_LOCATION_FEEDS -> {

            }
            FeedsType.HOT_RANKING_FEEDS -> {

            }
        }
    }

    private fun loadMyFeeds() {
        loadMyFeedsUseCase.execute()
    }

    override fun saveState() {
        handle[KEY_MY_FEEDS] = _myFeeds
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    companion object {
        private const val KEY_MY_FEEDS = "keyMyFeeds"
    }
}