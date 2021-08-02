package com.example.bikini_android.repository.account

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author bsgreentea
 */

@Parcelize
data class UserInfo(
    @SerializedName("username")
    val userName: String
) : Parcelable