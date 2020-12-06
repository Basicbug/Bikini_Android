package com.example.bikini_android.repository.feed

import com.google.android.gms.maps.model.LatLng

/**
 * @author bsgreentea
 */
@Suppress("unused")
data class Feed(
    var feedNumOfUser: Int = 0,
    var userId: String = "sampleUserId",
    var content: String = "sampleContent",
    var imageUrl: String = "sampleImageUri",
    var profileImageUrl: String = "sampleProfile",
    val position: LatLng,
    var countOfGroupFeed: Int = 1
)