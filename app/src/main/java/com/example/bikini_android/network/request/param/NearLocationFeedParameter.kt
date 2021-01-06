/*
 * NearLocationFeedsParameter.kt 2021. 1. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.request.param

import com.google.android.gms.maps.model.LatLng

/**
 * @author MyeongKi
 */

class NearLocationFeedParameter : RestApiParameter() {
    fun setLocation(value: LatLng) {
        put(RestApiParameterKey.LATITUDE, value.latitude.toString())
        put(RestApiParameterKey.LONGITUDE, value.longitude.toString())
    }
    fun setRadius(value: Double) {
        put(RestApiParameterKey.RADIUS, value.toString())
    }

    fun getLocation(): LatLng {
        return LatLng(get(RestApiParameterKey.LATITUDE).toDouble(), get(RestApiParameterKey.LONGITUDE).toDouble())
    }
    fun getRadius(): Double {
        return get(RestApiParameterKey.RADIUS).toDouble()
    }
}