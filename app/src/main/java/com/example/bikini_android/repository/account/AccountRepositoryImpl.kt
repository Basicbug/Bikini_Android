package com.example.bikini_android.repository.account

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.UserService
import com.example.bikini_android.util.error.ErrorToastHelper
import com.example.bikini_android.util.logging.Logger
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */
object AccountRepositoryImpl : AccountRepository {

    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "AccountRepositoryImpl"
        }
    }

    override fun getUserFromRemote(userInfo: UserInfo): Single<String?> {
        return ApiClientHelper
            .createMainApiByService(UserService::class)
            .updateUserInfo(LoginManagerProxy.jwt, userInfo)
            .subscribeOn(Schedulers.io())
            .map {
                it.message
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    override fun getMyInfoFromRemote(): Single<Pair<UserInfo?, String>> {
        return ApiClientHelper
            .createMainApiByService(UserService::class)
            .getMyInfo(LoginManagerProxy.jwt)
            .subscribeOn(Schedulers.io())
            .map {
                Pair(it.result, it.code)
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

}