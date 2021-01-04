/*
 * FeedRepositoryImpl.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.FeedService
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author MyeongKi
 */

class FeedRepositoryImpl private constructor() : FeedRepository {
    override fun getUserFeedsFromRemote(userId: String): Single<List<Feed>> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getUserFeeds(userId)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.result?.feeds
            }
    }

    override fun getRankingFeedsFromRemote(limit: Int): Single<List<Feed>> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getRankFeeds(limit)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map {
                it.result?.feeds
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