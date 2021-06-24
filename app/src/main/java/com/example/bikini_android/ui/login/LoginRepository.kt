package com.example.bikini_android.ui.login

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.AuthService
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
            TAG = "FeedRepositoryImpl"
        }
    }

    fun sendTokenToServer(accessToken: String): Single<LoginJwtResult> {
        return ApiClientHelper
            .createMainApiByService(AuthService::class)
            .loginNaver(accessToken = accessToken)
            .subscribeOn(Schedulers.io())
            .map { LoginJwtResult(it.result) }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    data class LoginJwtResult(val jwt: String?)
}