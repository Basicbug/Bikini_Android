package com.example.bikini_android.repository.feed

import android.os.Parcelable
import androidx.annotation.Keep
import com.basicbug.core.string.EMPTY_STRING
import com.example.bikini_android.repository.likes.Likes
import com.example.bikini_android.util.map.LocationUtils
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem
import kotlinx.parcelize.Parcelize

/**
 * @author bsgreentea
 */
@Parcelize
@Keep
data class Feed(
    var feedId: String = "",
    var feedNumOfUser: Int = 0,
    var numOfLikes: Int = 0,
    var username: String = "",
    var imageIds: List<Int>? = null,
    var content: String = "",
    var imageUrl: List<String>? = null,
    val locationInfo: LocationInfo?,
    var modifiedAt: String = "",
    var likes: Likes? = null
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