package com.example.bikini_android.repository.account

import com.basicbug.core.util.error.ErrorToastHelper
import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelperImpl
import com.example.bikini_android.network.request.service.UserService
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

    override fun updateUserInfo(userUpdateInfo: UserUpdateInfo): Single<UserInfo?> {
        return ApiClientHelperImpl
            .createMainApiByService(UserService::class)
            .updateUserInfo(LoginManagerProxy.accessToken, userUpdateInfo)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.userInfo
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    override fun getMyInfo(): Single<UserInfo?> {
        return ApiClientHelperImpl
            .createMainApiByService(UserService::class)
            .getMyInfo(LoginManagerProxy.accessToken)
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.userInfo
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                UserInfo("", 0, 0)
            }
    }

}