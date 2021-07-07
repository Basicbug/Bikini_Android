/*
 * DefaultSchedulerProvider.kt 2021. 6. 22
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.provider

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * @author MyeongKi
 */
class DefaultSchedulerProvider : SchedulerProvider {
    override fun io() =
        Schedulers.io()

    override fun computation() =
        Schedulers.computation()

    override fun main() =
        AndroidSchedulers.mainThread()
}