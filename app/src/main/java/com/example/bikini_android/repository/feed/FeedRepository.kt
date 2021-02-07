/*
 * FeedRepository.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import com.google.android.gms.maps.model.LatLng
import io.reactivex.Single

/**
 * @author MyeongKi
 */

interface FeedRepository {
    fun getUserFeedsFromRemote(
        userId: String
    ): Single<List<Feed>>

    fun getRankingFeedsFromRemote(
        limit: Int
    ): Single<List<Feed>>

    fun getAllFeedsFromRemote(): Single<List<Feed>>

    fun getNearbyFeedsFromRemote(
        latLng: LatLng,
        radius: Float
    ): Single<List<Feed>>
}