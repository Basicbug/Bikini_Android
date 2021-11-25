/*
 * TestSchedulerProvider.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.rx

import io.reactivex.schedulers.TestScheduler

/**
 * @author MyeongKi
 */
class TestSchedulerProvider : SchedulerProvider {

    val testScheduler = TestScheduler()

    override fun io() =
        testScheduler

    override fun computation() =
        testScheduler

    override fun main() =
        testScheduler
}