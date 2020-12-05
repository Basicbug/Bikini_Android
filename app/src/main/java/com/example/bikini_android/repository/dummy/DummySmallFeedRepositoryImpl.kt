/*
 * DummySmallFeedRepositoryImpl.kt 2020. 11. 28
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.dummy

import com.example.bikini_android.network.client.ApiClientHelper
import com.example.bikini_android.network.request.service.DummyService
import com.example.bikini_android.network.response.DummyImageResponse
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author MyeongKi
 */

class DummySmallFeedRepositoryImpl : DummyRepository {
    override fun getImages(): Single<DummyImageResponse.Result> {
        return ApiClientHelper.createMainApiByService(DummyService::class)
            .getImages()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .map { it.response?.result }
    }
}