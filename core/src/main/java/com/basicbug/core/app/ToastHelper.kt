/*
 * ToastHelper.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.app

import android.widget.Toast
import androidx.annotation.StringRes
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */
object ToastHelper {
    private var toast: Toast? = null

    fun show(@StringRes stringResId: Int) {
        show(AppResources.getString(stringResId))
    }

    fun show(message: String) {
        cancel()
        AndroidSchedulers.mainThread().scheduleDirect {
            toast = Toast.makeText(AppResources.getContext(), message, Toast.LENGTH_SHORT).also {
                it.show()
            }
        }
    }

    fun cancel() {
        AndroidSchedulers.mainThread().scheduleDirect {
            toast?.cancel()
        }
    }
}