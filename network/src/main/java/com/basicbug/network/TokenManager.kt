/*
 * TokenManager.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network

/**
 * @author MyeongKi
 */
interface TokenManager {
    var accessToken: String
    var refreshToken: String
}