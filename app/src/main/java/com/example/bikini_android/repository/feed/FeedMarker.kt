/*
 * FeedMarker.kt 2020. 11. 30
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import com.google.android.gms.maps.model.LatLng

/**
 * @author MyeongKi
 */

data class FeedMarker(
    @FeedMarkerId
    val id: String,
    val position: LatLng,
    val imageUrl: String
)

annotation class FeedMarkerId
