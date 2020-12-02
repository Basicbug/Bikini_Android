/*
 * FeedMarkerImageLoadEvent.kt 2020. 11. 29
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import com.example.bikini_android.repository.feed.FeedMarker
import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */
class FeedMarkerImageLoadEvent(val feedMarker: FeedMarker) : RxAction