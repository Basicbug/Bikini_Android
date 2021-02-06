/*
 * LoadFeedsUseCase.kt 2021. 1. 30
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.google.android.gms.maps.model.LatLng

/**
 * @author MyeongKi
 */
interface LoadFeedsUseCase {
    fun execute() = Unit
    fun execute(latLng: LatLng, radius: Double) = Unit
}