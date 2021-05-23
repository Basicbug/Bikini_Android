/*
 * FeedRepositoryImpl.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.param.NearbyFeedParameter
import com.example.bikini_android.network.request.service.FeedService
import com.example.bikini_android.network.request.service.ImagesService
import com.example.bikini_android.network.response.DefaultResponse
import com.example.bikini_android.util.error.ErrorToastHelper
import com.example.bikini_android.util.logging.Logger
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

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
            .getUserFeeds(LoginManagerProxy.jwt, userId)
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
            .getRankFeeds(LoginManagerProxy.jwt, limit)
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
            .getAllFeeds(LoginManagerProxy.jwt)
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
            .getNearbyLocationFeeds(
                LoginManagerProxy.jwt,
                NearbyFeedParameter().apply {
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

    override fun addFeedToRemote(
        feed: Feed,
        imageFiles: List<MultipartBody.Part>
    ): Single<DefaultResponse?> {
        return ApiClientHelper
            .createMainApiByService(ImagesService::class)
            .uploadImages(imageFiles)
            .subscribeOn(Schedulers.io())
            .map { response ->
                mutableListOf<Int>().apply {
                    response.result?.forEach {
                        add(it.id)
                    }
                }
            }
            .flatMap { imageIds ->
                ApiClientHelper.createMainApiByService(FeedService::class)
                    .addFeed(
                        LoginManagerProxy.jwt,
                        feed.apply {
                            this.imageIds = imageIds
                        })
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
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
