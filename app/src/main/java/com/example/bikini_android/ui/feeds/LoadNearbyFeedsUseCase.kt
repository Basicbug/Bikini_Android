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
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.rx.addTo
import com.google.android.gms.maps.model.LatLng
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
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

    override fun execute(latLng: LatLng, radius: Float) {
        when (getNearbyLoadCase(latLng, radius)) {
            NearbyLoadCase.LOAD_REMOTE_CASE -> {
                loadNearbyFeedsFromRemote(latLng, radius)
            }
            else -> {
                loadNearbyFeedsFromLocal(latLng, radius)
            }
        }
    }

    override fun execute() {
        logger.error { "맵 -> 다른 화면으로 전이된 경우에 랜더링된 피드가 없는 경우의 에" }
    }

    private fun getNearbyLoadCase(latLng: LatLng, radius: Float): NearbyLoadCase {
        nearbyFeedsInfoCached?.let {
            return if (it.latLng != latLng || it.biggestRadius < radius) {
                NearbyLoadCase.LOAD_REMOTE_CASE
            } else {
                NearbyLoadCase.LOAD_LOCAL_CASE
            }
        }
        return NearbyLoadCase.LOAD_REMOTE_CASE
    }

    private fun loadNearbyFeedsFromRemote(latLng: LatLng, radius: Float) {
        feedsRepository
            .getNearbyFeedsFromRemote(latLng, radius)
            .subscribe { result ->
                result?.let {
                    cacheNearbyFeedsInfo(latLng, radius, it)
                    itemEventRelay.accept(FeedsEvent(it, FeedsType.NEARBY_FEEDS))
                }
            }
            .addTo(disposable)
    }

    private fun loadNearbyFeedsFromLocal(latLng: LatLng, radius: Float) {
        nearbyFeedsInfoCached?.feeds?.let { originalFeeds ->
            val feedsFiltered = mutableListOf<Feed>()
            originalFeeds.forEach {
                if (isNearbyFeed(latLng, radius, it)) {
                    feedsFiltered.add(it)
                }
            }
            cacheNearbyFeedsInfo(latLng, radius, originalFeeds)
            itemEventRelay.accept(FeedsEvent(feedsFiltered, FeedsType.NEARBY_FEEDS))
        }
    }

    private fun isNearbyFeed(latLng: LatLng, radius: Float, feed: Feed): Boolean {
        feed.locationInfo?.let {
            return LocationUtils.getDistanceBetween(
                latLng,
                LatLng(it.latitude, it.longitude)
            ) <= radius
        }
        return false
    }

    private fun cacheNearbyFeedsInfo(
        currentLatLng: LatLng,
        currentRadius: Float,
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
        val biggestRadius: Float,
        val feeds: List<Feed>
    )

    companion object {
        private const val MIN_RADIUS = 0.0f
    }
}