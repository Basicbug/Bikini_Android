/*
 * FeedRepositoryImpl.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.param.NearbyFeedParameter
import com.example.bikini_android.network.request.service.FeedService
import com.example.bikini_android.util.error.ErrorToastHelper
import com.example.bikini_android.util.logging.Logger
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author MyeongKi
 */

class FeedRepositoryImpl private constructor() : FeedRepository {
    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "FeedRepositoryImpl"
        }
    }

    override fun getUserFeedsFromRemote(userId: String): Single<List<Feed>?> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getUserFeeds(userId)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun getRankingFeedsFromRemote(limit: Int): Single<List<Feed>?> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getRankFeeds(limit)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun getAllFeedsFromRemote(): Single<List<Feed>?> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getAllFeeds()
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun getNearbyFeedsFromRemote(latLng: LatLng, radius: Float): Single<List<Feed>?> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getNearbyLocationFeeds(NearbyFeedParameter().apply {
                setLocation(latLng)
                setRadius(radius)
            })
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    companion object {
        fun getInstance(): FeedRepository {
            return LazyHolder.INSTANCE
        }
    }

    private object LazyHolder {
        val INSTANCE = FeedRepositoryImpl()
    }
}