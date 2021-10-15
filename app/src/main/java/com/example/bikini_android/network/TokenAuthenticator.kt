/*
 * TokenAuthenticator.kt 2021. 10. 14
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network

import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.manager.PreferenceManager
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.AuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * @author MyeongKi
 */
class TokenAuthenticator : Authenticator {
    override fun authenticate(route: Route?, response: Response): Request {
        updatedToken()
        return response.request.newBuilder()
            .header(
                "X-AUTH-TOKEN", LoginManagerProxy.jwt
            )
            .build()
    }

    private fun updatedToken() {
        val authTokenResponse = ApiClientHelper
            .createMainApiByService(AuthService::class)
            .refreshAccessToken(
                PreferenceManager.getString(AppResources.getString(R.string.jwt)),
                LoginManagerProxy.refreshToken
            )
            .blockingGet()
        authTokenResponse.result?.let {
            LoginManagerProxy.jwt = it.accessToken
            LoginManagerProxy.refreshToken = it.refreshToken
            return
        }
        throw NullPointerException("refresh api fail")
    }
}