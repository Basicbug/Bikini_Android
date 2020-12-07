package com.example.bikini_android.repository.feed

import android.os.Parcelable
import com.google.android.gms.maps.model.LatLng
import kotlinx.android.parcel.Parcelize

/**
 * @author bsgreentea
 */
@Suppress("unused")
@Parcelize
data class Feed(
    var feedNumOfUser: Int = 0,
    var userId: String = "sampleUserId",
    var content: String = "sampleContent",
    var imageUrl: String = "sampleImageUri",
    var profileImageUrl: String = "sampleProfile",
    val position: LatLng?,
    var countOfGroupFeed: Int = 1
) : Parcelable