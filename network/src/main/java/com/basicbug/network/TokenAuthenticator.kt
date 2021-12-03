/*
 * TokenAuthenticator.kt 2021. 12. 3
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.network

import com.basicbug.core.app.AppResources
import com.basicbug.core.manager.PreferenceManager
import com.basicbug.core.util.logging.Logger
import com.basicbug.network.client.ApiClientHelper
import com.basicbug.network.request.AuthService
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

/**
 * @author MyeongKi
 */
class TokenAuthenticator(
    private val apiClientHelper: ApiClientHelper,
    private val tokenManager: TokenManager,
    private val preferenceManager: PreferenceManager
) : Authenticator {
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
                "X-AUTH-TOKEN", tokenManager.accessToken
            )
            .build()
    }

    private fun updatedToken() {
        val authTokenResponse = apiClientHelper
            .createInvalidAuthApiByService(AuthService::class)
            .refreshAccessToken(
                preferenceManager.getString(AppResources.getString(R.string.access_token)),
                tokenManager.refreshToken
            )
            .onErrorReturn {
                null
            }
            .blockingGet()

        authTokenResponse.result?.let {
            tokenManager.accessToken = it.accessToken
            tokenManager.refreshToken = it.refreshToken
            return
        }
        throw NullPointerException("refresh api fail ${authTokenResponse.result.toString()}")
    }
}