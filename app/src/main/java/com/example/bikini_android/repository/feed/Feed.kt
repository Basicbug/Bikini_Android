package com.example.bikini_android.repository.feed

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.bikini_android.util.string.EMPTY_STRING
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
    var imageIds: List<Int>? = null,
    var content: String = "sampleContent",
    var imageUrl: List<String>? = null,
    var profileImageUrl: String = "sampleProfile",
    val locationInfo: LocationInfo?,
    var countOfGroupFeed: Int = 1
) : Parcelable

fun Feed.firstImageUrl(): String {
    return imageUrl?.let {
        if (it.isNotEmpty()) {
            it.first()
        } else {
            EMPTY_STRING
        }
    } ?: EMPTY_STRING
}
