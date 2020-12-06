/*
 * DummyRepository.kt 2020. 11. 28
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.repository.dummy

import com.example.bikini_android.network.response.DummyImageResponse
import io.reactivex.Single

/**
 * @author MyeongKi
 */

interface DummyRepository {
    fun getImages(): Single<DummyImageResponse.Result>
}