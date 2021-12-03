package com.example.bikini_android.repository.account

import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.basicbug.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.UserService
import com.example.bikini_android.network.response.MyInfoReponse
import com.example.bikini_android.network.response.UserUpdateResponse
import com.basicbug.core.util.error.ErrorToastHelper
import com.example.bikini_android.network.client.ApiClientHelperImpl
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

    override fun getUserFromRemote(userInfo: UserInfo): Single<UserUpdateResponse.Result?> {
        return ApiClientHelperImpl
            .createMainApiByService(UserService::class)
            .updateUserInfo(LoginManagerProxy.accessToken, userInfo)
            .subscribeOn(Schedulers.io())
            .map {
                it.result
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    override fun getMyInfoFromRemote(): Single<MyInfoReponse.Result?> {
        return ApiClientHelperImpl
            .createMainApiByService(UserService::class)
            .getMyInfo(LoginManagerProxy.accessToken)
            .subscribeOn(Schedulers.io())
            .map {
                it.result
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

}