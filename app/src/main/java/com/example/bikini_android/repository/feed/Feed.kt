package com.example.bikini_android.repository.feed

import android.os.Parcel
import android.os.Parcelable
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
    val position: LatLng?,
    var countOfGroupFeed: Int = 1
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readString() ?: "",
        parcel.readParcelable(LatLng::class.java.classLoader),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(feedNumOfUser)
        parcel.writeString(userId)
        parcel.writeString(content)
        parcel.writeString(imageUrl)
        parcel.writeString(profileImageUrl)
        parcel.writeParcelable(position, flags)
        parcel.writeInt(countOfGroupFeed)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Feed> {
        override fun createFromParcel(parcel: Parcel): Feed {
            return Feed(parcel)
        }

        override fun newArray(size: Int): Array<Feed?> {
            return arrayOfNulls(size)
        }
    }
}