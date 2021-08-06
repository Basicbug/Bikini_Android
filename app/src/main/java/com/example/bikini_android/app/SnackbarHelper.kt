/*
 * SnackbarHelper.kt 2021. 3. 24
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.app

import android.app.Activity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.StringRes
import com.example.bikini_android.R
import com.google.android.material.snackbar.Snackbar

/**
 * @author MyeongKi
 */
object SnackbarHelper {
    private var lastCallTag = ""

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
        return view?.findViewById(R.id.constraint_layout)
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