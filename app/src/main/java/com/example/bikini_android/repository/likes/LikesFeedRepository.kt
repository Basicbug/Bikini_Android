/*
 * LikeFeedRepository.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

import com.basicbug.core.util.error.ErrorToastHelper
import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelperImpl
import com.example.bikini_android.network.request.param.LikesParameter
import com.example.bikini_android.network.request.service.LikesService
import com.example.bikini_android.network.response.convertLikes
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * @author MyeongKi
 */
class LikesFeedRepository private constructor() : LikesRepository {
    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "LikesFeedRepository"
        }
    }

    override fun addLikes(targetId: String): Single<Likes?> {
        return ApiClientHelperImpl
            .createMainApiByService(LikesService::class)
            .addLikes(LoginManagerProxy.accessToken, getLikesParameter(targetId))
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.convertLikes()
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    override fun removeLikes(targetId: String): Single<Likes?> {
        return ApiClientHelperImpl
            .createMainApiByService(LikesService::class)
            .removeLikes(LoginManagerProxy.accessToken, getLikesParameter(targetId))
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.convertLikes()
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    override fun checkLikes(targetId: String): Single<Likes?> {
        return ApiClientHelperImpl
            .createMainApiByService(LikesService::class)
            .checkLikes(LoginManagerProxy.accessToken, getLikesParameter(targetId))
            .subscribeOn(Schedulers.io())
            .map {
                it.result?.convertLikes()
            }
            .onErrorReturn { throwable ->
                ErrorToastHelper.unknownError(logger, throwable)
                null
            }
    }

    private fun getLikesParameter(targetId: String): LikesParameter = LikesParameter().apply {
        setLikesParameter(targetId, LikesTargetType.FEED)
    }

    companion object {
        fun getInstance(): LikesRepository {
            return LazyHolder.INSTANCE
        }
    }

    private object LazyHolder {
        val INSTANCE = LikesFeedRepository()
    }
}