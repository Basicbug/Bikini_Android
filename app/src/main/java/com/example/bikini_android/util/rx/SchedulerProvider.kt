/*
 * SchedulerProvider.kt 2021. 6. 22
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.rx

import io.reactivex.Scheduler

/**
 * @author MyeongKi
 */
interface SchedulerProvider {
    fun io(): Scheduler
    fun computation(): Scheduler
    fun main(): Scheduler
}