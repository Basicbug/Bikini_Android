/*
 * SnackbarHelper.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.IdRes
import androidx.annotation.StringRes
import com.basicbug.core.R
import com.google.android.material.snackbar.Snackbar

/**
 * @author MyeongKi
 */
object SnackbarHelper {
    private var lastCallTag = ""

    @IdRes
    private var anchorLayoutId: Int? = null
    fun show(activity: Activity, @StringRes stringResId: Int, callTag: String) {
        show(activity, AppResources.getString(stringResId), callTag)
    }

    fun show(activity: Activity, snackbarMessage: String, callTag: String) {
        val decorView = getDecorView(activity)
        val anchorView = getAnchorView(decorView)
        if (lastCallTag != callTag) {
            lastCallTag = callTag
            anchorView?.let {
                val snackbar = Snackbar.make(it, snackbarMessage, Snackbar.LENGTH_INDEFINITE)
                snackbar.setAction(R.string.confirm) {
                    snackbar.dismiss()
                    lastCallTag = ""
                }
                snackbar.show()
            }
        }
    }

    private fun getAnchorView(view: ViewGroup?): View? {
        anchorLayoutId?.let {
            return view?.findViewById(it)
        }
        return null
    }

    private fun getDecorView(activity: Activity): ViewGroup? {
        if (activity.isFinishing) {
            return null
        }
        val window = activity.window
        window?.let {
            return it.decorView as ViewGroup
        }
        return null
    }
}