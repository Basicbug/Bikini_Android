package com.example.bikini_android.repository.feed

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

/**
 * @author bsgreentea
 */
@Parcelize
@Keep
data class Feed(
    var feedId: String = "test1",
    var feedNumOfUser: Int = 0,
    var userId: String = "sampleUserId",
    var content: String = "sampleContent",
    var imageUrl: String = "sampleImageUri",
    var profileImageUrl: String = "sampleProfile",
    val locationInfo: LocationInfo?,
    var countOfGroupFeed: Int = 1
) : Parcelable
