package com.example.bikini_android.ui.login

import com.basicbug.core.util.error.ErrorToastHelper
import com.basicbug.core.util.logging.Logger
import com.basicbug.network.request.AuthService
import com.basicbug.network.response.TokenResponse
import com.example.bikini_android.network.client.ApiClientHelperImpl
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */
class LoginRepository {

    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "LoginRepositoryImpl"
        }
    }

    fun sendTokenToServer(accessToken: String): Single<TokenResponse.Result?> {
        return ApiClientHelperImpl
            .createMainApiByService(AuthService::class)
            .loginNaver(accessToken = accessToken)
            .subscribeOn(Schedulers.io())
            .map { it.result }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }
}