/*
 * ErrorConsumer.kt 2021. 4. 11
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.error

import com.example.bikini_android.R
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.util.logging.Logger

/**
 * @author MyeongKi
 */
object ErrorToastHelper {
    fun unknownError(logger: Logger, throwable: Throwable) {
        logger.error { throwable.toString() }
        ToastHelper.show(R.string.unknown_error_message)
    }
}
