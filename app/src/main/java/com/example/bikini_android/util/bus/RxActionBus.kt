/*
 * RxActionBus.kt 2020. 10. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.bus

import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

/**
 * @author MyeongKi
 */

object RxActionBus {
    private val bus: Relay<RxAction> = PublishRelay.create<RxAction>().toSerialized()

    fun post(event: RxAction) {
        bus.accept(event)
    }

    fun <T> toObservable(eventType: Class<T>): Observable<T> {
        return bus.ofType(eventType)
    }

    fun hasObservers(): Boolean {
        return bus.hasObservers()
    }
}
