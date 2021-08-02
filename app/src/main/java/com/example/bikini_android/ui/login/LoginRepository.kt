package com.example.bikini_android.ui.login

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.AuthService
import com.example.bikini_android.network.response.LoginResponse
import com.example.bikini_android.util.error.ErrorToastHelper
import com.example.bikini_android.util.logging.Logger
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

    fun sendTokenToServer(accessToken: String): Single<LoginResponse.Result?> {
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