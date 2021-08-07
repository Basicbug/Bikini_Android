/*
 * LikeFeedRepository.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.likes

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.param.LikesParameter
import com.example.bikini_android.network.request.service.LikesService
import com.example.bikini_android.network.response.convertLikes
import com.example.bikini_android.util.error.ErrorToastHelper
import com.example.bikini_android.util.logging.Logger
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
        return ApiClientHelper
            .createMainApiByService(LikesService::class)
            .addLikes(LoginManagerProxy.jwt, getLikesParameter(targetId))
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
        return ApiClientHelper
            .createMainApiByService(LikesService::class)
            .removeLikes(LoginManagerProxy.jwt, getLikesParameter(targetId))
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
        return ApiClientHelper
            .createMainApiByService(LikesService::class)
            .checkLikes(LoginManagerProxy.jwt, getLikesParameter(targetId))
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
        setLikesParameter(targetId, Likes.TargetType.FEED)
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