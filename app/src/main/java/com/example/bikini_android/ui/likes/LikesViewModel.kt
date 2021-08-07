/*
 * LikeViewModel.kt 2021. 8. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.likes

import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.concurrent.TimeUnit

/**
 * @author MyeongKi
 */
class LikesViewModel(
    private val disposables: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>,
) {
    private val likesUseCase = LikesUseCase(disposables)

    init {
        observeEvent()
    }

    private fun observeEvent() {
        itemEventRelay
            .ofType(LikesItemViewModel.EventType::class.java)
            .debounce(300L, TimeUnit.MILLISECONDS)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe {
                likesUseCase.execute(it)
            }.addTo(disposables)
    }
}