/*
 * ErrorToastHelper.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.error

import com.basicbug.core.R
import com.basicbug.core.app.ToastHelper
import com.basicbug.core.util.logging.Logger

/**
 * @author MyeongKi
 */
object ErrorToastHelper {
    fun unknownError(logger: Logger, throwable: Throwable) {
        logger.error { throwable.toString() }
        ToastHelper.show(R.string.unknown_error_message)
    }
}