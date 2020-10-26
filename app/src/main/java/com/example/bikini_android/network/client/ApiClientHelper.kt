/*
 * ApiClientHelper.kt 2020. 10. 27
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network.client

import com.example.bikini_android.network.NetworkConstants

/**
 * @author MyeongKi
 */

object ApiClientHelper {
    val serviceApiClient = ServiceApiFactory.getApiClient(NetworkConstants.BASE_URL)
}