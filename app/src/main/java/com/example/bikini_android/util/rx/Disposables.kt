/*
 * Disposables.kt 2020. 10. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.rx

import io.reactivex.disposables.Disposable
import io.reactivex.internal.disposables.DisposableContainer

/**
 * @author MyeongKi
 */

fun Disposable.addTo(
    disposableContainer: DisposableContainer
): Disposable = apply {
    disposableContainer.add(this)
}
