/*
 * TokenAuthenticator.kt 2021. 10. 14
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.network

import com.basicbug.core.app.AppResources
import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.R
import com.example.bikini_android.manager.PreferenceManagerImpl
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
    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "TokenAuthenticator"
        }
    }

    override fun authenticate(route: Route?, response: Response): Request {
        try {
            updatedToken()
        } catch (e: NullPointerException) {
            logger.error { e.toString() }
        }

        return response.request.newBuilder()
            .header(
                "X-AUTH-TOKEN", LoginManagerProxy.accessToken
            )
            .build()
    }

    private fun updatedToken() {
        val authTokenResponse = ApiClientHelper
            .createInvalidAuthApiByService(AuthService::class)
            .refreshAccessToken(
                PreferenceManagerImpl.getString(AppResources.getString(R.string.access_token)),
                LoginManagerProxy.refreshToken
            )
            .onErrorReturn {
                null
            }
            .blockingGet()

        authTokenResponse.result?.let {
            LoginManagerProxy.accessToken = it.accessToken
            LoginManagerProxy.refreshToken = it.refreshToken
            return
        }
        throw NullPointerException("refresh api fail ${authTokenResponse.result.toString()}")
    }
}