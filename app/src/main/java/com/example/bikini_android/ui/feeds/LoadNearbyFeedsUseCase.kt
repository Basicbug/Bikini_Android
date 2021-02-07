/*
 * LoadNearFeedsUseCase.kt 2021. 1. 1
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
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import kotlin.math.max

/**
 * @author MyeongKi
 */

class LoadNearbyFeedsUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>
) : LoadFeedsUseCase {
    private val logger = Logger().apply {
        TAG = this@LoadNearbyFeedsUseCase.javaClass.simpleName
    }
    private val feedsRepository = FeedRepositoryInjector.getFeedRepositoryImpl()
    private var nearbyFeedsInfoCached: NearbyFeedsInfo? = null

    override fun execute(lastFeedsRendered: List<Feed>) {
        if (lastFeedsRendered.isNotEmpty()) {
            itemEventRelay.accept(FeedsEvent(lastFeedsRendered, FeedsType.NEARBY_FEEDS))
        } else {
            logger.error { "맵 -> 다른 화면으로 전이된 경우에 랜더링된 피드가 없는 경우의 에" }
        }
    }

    override fun execute(latLng: LatLng, radius: Double) {
        when (getNearbyLoadCase(latLng, radius)) {
            NearbyLoadCase.LOAD_REMOTE_CASE -> {
                loadNearbyFeedsFromRemote(latLng, radius)
            }
            else -> {
                loadNearbyFeedsFromLocal(latLng, radius)
            }
        }
    }

    private fun getNearbyLoadCase(latLng: LatLng, radius: Double): NearbyLoadCase {
        nearbyFeedsInfoCached?.let {
            return if (it.latLng != latLng || it.biggestRadius < radius) {
                NearbyLoadCase.LOAD_REMOTE_CASE
            } else {
                NearbyLoadCase.LOAD_LOCAL_CASE
            }
        }
        return NearbyLoadCase.LOAD_REMOTE_CASE
    }

    private fun loadNearbyFeedsFromRemote(latLng: LatLng, radius: Double) {
        feedsRepository
            .getNearbyFeedsFromRemote(latLng, radius)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({
                cacheNearbyFeedsInfo(latLng, radius, it)
                itemEventRelay.accept(FeedsEvent(it, FeedsType.NEARBY_FEEDS))
            }, {
                logger.error { it.toString() }
            })
            .addTo(disposable)
    }

    private fun loadNearbyFeedsFromLocal(latLng: LatLng, radius: Double) {
        nearbyFeedsInfoCached?.feeds?.let { originalFeeds ->
            val feedsFiltered = mutableListOf<Feed>()
            originalFeeds.forEach {
                if (isNearbyFeed(it)) {
                    feedsFiltered.add(it)
                }
            }
            cacheNearbyFeedsInfo(latLng, radius, originalFeeds)
            itemEventRelay.accept(FeedsEvent(feedsFiltered, FeedsType.NEARBY_FEEDS))
        }
    }

    //TODO FIXME
    private fun isNearbyFeed(feed: Feed): Boolean {
        return true
    }

    private fun cacheNearbyFeedsInfo(
        currentLatLng: LatLng,
        currentRadius: Double,
        feedsLoaded: List<Feed>
    ) {
        nearbyFeedsInfoCached =
            NearbyFeedsInfo(
                currentLatLng,
                max(nearbyFeedsInfoCached?.biggestRadius ?: MIN_RADIUS, currentRadius),
                feedsLoaded
            )
    }

    private enum class NearbyLoadCase {
        LOAD_REMOTE_CASE,
        LOAD_LOCAL_CASE
    }

    private data class NearbyFeedsInfo(
        val latLng: LatLng,
        val biggestRadius: Double,
        val feeds: List<Feed>
    )

    companion object {
        private const val MIN_RADIUS = 0.0
    }
}