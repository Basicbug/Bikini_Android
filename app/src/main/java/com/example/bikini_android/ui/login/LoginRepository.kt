package com.example.bikini_android.ui.login

import com.basicbug.core.util.error.ErrorToastHelper
import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.AuthService
import com.example.bikini_android.network.response.TokenResponse
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
        return ApiClientHelper
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