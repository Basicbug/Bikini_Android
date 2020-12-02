package com.example.bikini_android.repository.feed

import com.google.android.gms.maps.model.LatLng

/**
 * @author bsgreentea
 */
data class Feed(
    var feedNumOfUser: Int = 0,
    var userId: String = "sampleUserId",
    var content: String = "sampleContent",
    val position: LatLng,
    val imageUrl: String
)