/*
 * FeedMarkerImageLoadEvent.kt 2020. 11. 29
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.map

import com.example.bikini_android.repository.feed.Feed
import com.basicbug.core.util.bus.RxAction

/**
 * @author MyeongKi
 */
class FeedMarkerImageLoadEvent(val feed: Feed) : RxAction