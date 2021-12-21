/*
 * FeedRepositoryImpl.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import com.basicbug.core.util.error.ErrorToastHelper
import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelperImpl
import com.example.bikini_android.network.request.param.NearbyFeedParameter
import com.example.bikini_android.network.request.service.FeedService
import com.example.bikini_android.network.request.service.ImagesService
import com.example.bikini_android.network.response.DefaultResponse
import com.example.bikini_android.repository.likes.LikesRepositoryInjector
import com.example.bikini_android.repository.likes.LikesTargetType
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

/**
 * @author MyeongKi
 */

class FeedRepositoryImpl private constructor() : FeedRepository {
    private val likesRepository = LikesRepositoryInjector.getLikesRepository(LikesTargetType.FEED)
    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "FeedRepositoryImpl"
        }
    }

    override fun getUserFeedsFromRemote(userId: String): Single<List<Feed>?> {
        return ApiClientHelperImpl
            .createMainApiByService(FeedService::class)
            .getUserFeeds(LoginManagerProxy.accessToken, userId)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .flattenAsFlowable { it }
            .flatMap { feed ->
                likesRepository
                    .checkLikes(feed.feedId)
                    .doOnSuccess {
                        feed.likes = it
                    }
                    .map {
                        feed
                    }
                    .toFlowable()
            }
            .toList()
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun getRankingFeedsFromRemote(limit: Int): Single<List<Feed>?> {
        return ApiClientHelperImpl
            .createMainApiByService(FeedService::class)
            .getRankFeeds(LoginManagerProxy.accessToken, limit)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .flattenAsFlowable { it }
            .flatMap { feed ->
                likesRepository
                    .checkLikes(feed.feedId)
                    .doOnSuccess {
                        feed.likes = it
                    }
                    .map {
                        feed
                    }
                    .toFlowable()
            }
            .toList()
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun getAllFeedsFromRemote(): Single<List<Feed>?> {
        return ApiClientHelperImpl
            .createMainApiByService(FeedService::class)
            .getAllFeeds(LoginManagerProxy.accessToken)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .flattenAsFlowable { it }
            .flatMap { feed ->
                likesRepository
                    .checkLikes(feed.feedId)
                    .doOnSuccess {
                        feed.likes = it
                    }
                    .map {
                        feed
                    }
                    .toFlowable()
            }
            .toList()
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun getNearbyFeedsFromRemote(latLng: LatLng, radius: Float): Single<List<Feed>?> {
        return ApiClientHelperImpl
            .createMainApiByService(FeedService::class)
            .getNearbyLocationFeeds(
                LoginManagerProxy.accessToken,
                NearbyFeedParameter().apply {
                    setLocation(latLng)
                    setRadius(radius)
                })
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
            .flattenAsFlowable { it }
            .flatMap { feed ->
                likesRepository
                    .checkLikes(feed.feedId)
                    .doOnSuccess {
                        feed.likes = it
                    }
                    .map {
                        feed
                    }
                    .toFlowable()
            }
            .toList()
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                emptyList()
            }
    }

    override fun addFeedToRemote(
        feed: Feed,
        imageFiles: List<MultipartBody.Part>
    ): Single<DefaultResponse?> {
        return ApiClientHelperImpl
            .createMainApiByService(ImagesService::class)
            .uploadImages(LoginManagerProxy.accessToken, imageFiles)
            .subscribeOn(Schedulers.io())
            .map { response ->
                mutableListOf<Int>().apply {
                    response.result?.forEach {
                        add(it.id)
                    }
                }
            }
            .flatMap { imageIds ->
                ApiClientHelperImpl.createMainApiByService(FeedService::class)
                    .addFeed(
                        LoginManagerProxy.accessToken,
                        feed.apply {
                            this.imageIds = imageIds
                        })
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                DefaultResponse()
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