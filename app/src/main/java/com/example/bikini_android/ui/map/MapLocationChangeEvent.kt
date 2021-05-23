/*
 * MapLocationChangeEvent.kt 2021. 2. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import com.example.bikini_android.util.bus.RxAction
import com.google.android.gms.maps.model.LatLng

/**
 * @author MyeongKi
 */
class MapLocationChangeEvent(val latLng: LatLng, val visibleRadius: Float) : RxAction
