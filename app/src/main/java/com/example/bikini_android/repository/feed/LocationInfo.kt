/*
 * Location.kt 2021. 1. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.feed

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

/**
 * @author MyeongKi
 */
@Parcelize
data class LocationInfo(
    val latitude: Double,
    val longitude: Double,
    var locationName: String? = null
) : Parcelable