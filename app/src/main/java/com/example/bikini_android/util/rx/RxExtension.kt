/*
 * RxExtension.kt 2020. 10. 24
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.rx

import io.reactivex.Maybe
import io.reactivex.Single

/**
 * @author MyeongKi
 */

fun <T : Any> T.toSingleFromCallable(): Single<T> = Single.fromCallable { this }

fun <T : Any> T?.toMaybeFromCallable(): Maybe<T> = Maybe.fromCallable { this }