package com.example.bikini_android.ui.board

import com.example.bikini_android.util.bus.RxAction
import com.google.android.gms.maps.model.LatLng

/**
 * @author bsgreentea
 */
class FeedPostEvent(val latLng: LatLng) : RxAction