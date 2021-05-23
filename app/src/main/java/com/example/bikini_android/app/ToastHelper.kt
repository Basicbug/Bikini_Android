/*
 * ToastHelper.kt 2021. 3. 24
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.widget.Toast
import androidx.annotation.StringRes
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */
object ToastHelper {
    private var toast: Toast? = null

    fun show(@StringRes stringResId: Int) {
        show(AppResources.getStringResId(stringResId))
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
