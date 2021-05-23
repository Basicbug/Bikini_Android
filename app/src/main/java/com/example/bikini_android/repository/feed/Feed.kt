package com.example.bikini_android.repository.feed

import android.os.Parcelable
import androidx.annotation.Keep
import com.example.bikini_android.util.map.LocationUtils
import com.example.bikini_android.util.string.EMPTY_STRING
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
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
) : Parcelable, ClusterItem {
    override fun getPosition(): LatLng {
        return locationInfo?.convertLatLng() ?: throw NullPointerException("location info is null")
    }

    override fun getTitle(): String? {
        return null
    }

    override fun getSnippet(): String? {
        return null
    }
}

fun Feed.firstImageUrl(): String {
    return imageUrl?.let {
        if (it.isNotEmpty()) {
            it.first()
        } else {
            EMPTY_STRING
        }
    } ?: EMPTY_STRING
}

fun Feed.getDistanceFromMyLocation(): Float {
    return LocationUtils.getDistanceBetween(
        LocationUtils.getCurrentLatLng(),
        locationInfo?.convertLatLng()
    )
}
