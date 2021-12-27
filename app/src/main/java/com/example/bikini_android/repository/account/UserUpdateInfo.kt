/*
 * UserUpdateBody.kt 2021. 12. 16
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.account

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

/**
 * @author MyeongKi
 */
@Parcelize
data class UserUpdateInfo(
    @SerializedName("username")
    val userName: String
) : Parcelable