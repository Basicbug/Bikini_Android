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
import com.example.bikini_android.network.request.service.ImagesService
import com.example.bikini_android.network.response.DefaultResponse
import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import retrofit2.http.Part

/**
 * @author MyeongKi
 */

class FeedRepositoryImpl private constructor() : FeedRepository {
    override fun getUserFeedsFromRemote(userId: String): Single<List<Feed>> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getUserFeeds(userId)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
    }

    override fun getRankingFeedsFromRemote(limit: Int): Single<List<Feed>> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getRankFeeds(limit)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
    }

    override fun getAllFeedsFromRemote(): Single<List<Feed>> {
        return ApiClientHelper
            .createMainApiByService(FeedService::class)
            .getAllFeeds()
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.feeds
            }
    }

    override fun getNearbyFeedsFromRemote(latLng: LatLng, radius: Float): Single<List<Feed>> {
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
    }

    override fun addFeedToRemote(
        feed: Feed,
        imageFiles: List<MultipartBody.Part>
    ): Single<DefaultResponse> {
        return ApiClientHelper
            .createMainApiByService(ImagesService::class)
            .uploadImages(imageFiles)
            .flatMap {
                it.result?.url?.let {
                    ApiClientHelper.createMainApiByService(FeedService::class)
                        .addFeed(feed.apply {
                            imageUrl = it
                        })
                }
            }
            .subscribeOn(Schedulers.io())
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