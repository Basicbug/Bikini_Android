package com.example.bikini_android.ui.board

import com.example.bikini_android.repository.feed.LocationInfo
import com.example.bikini_android.util.bus.RxAction

/**
 * @author bsgreentea
 */
class FeedPostEvent(val locationInfo: LocationInfo) : RxAction
