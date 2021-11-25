/*
 * FeedMarkersLoadEvent.kt 2020. 12. 3
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.feeds

import com.example.bikini_android.repository.feed.Feed
import com.basicbug.core.util.bus.RxAction

/**
 * @author MyeongKi
 */

class FeedsEvent(val feeds: List<Feed>, val feedsType: FeedsType) : RxAction
