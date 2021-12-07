/*
 * DialogController.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.dialog

import android.content.Context
import android.content.DialogInterface
import androidx.annotation.StringRes
import androidx.appcompat.app.AlertDialog
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.basicbug.core.R
import com.basicbug.core.app.ToastHelper
import com.google.android.material.dialog.MaterialAlertDialogBuilder

/**
 * @author MyeongKi
 */

//FIXME 빌더패턴을 통하여 옵션을 넘겨주는 방식으로 리팩토링 고려.
object DialogController {
    private var alertDialog: AlertDialog? = null

    fun isShowDialog(): Boolean = alertDialog?.isShowing ?: false

    fun showAlertDialog(
        context: Context,
        @StringRes
        titleResId: Int,
        @StringRes
        msgResId: Int,
        closeCallback: DialogInterface.OnClickListener? = null,
        dismissCallback: () -> Unit = { }
    ) {
        if (isShowDialog())
            return
        alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(titleResId)
            .setMessage(msgResId)
            .setPositiveButton(R.string.close, closeCallback)
            .setCancelable(false)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                setOnDismissListener {
                    alertDialog = null
                    dismissCallback()
                }
                show()
            }
    }

    fun showConfirmationDialog(
        context: Context,
        @StringRes
        titleResId: Int,
        @StringRes
        msgResId: Int,
        confirmCallback: DialogInterface.OnClickListener? = null,
        closeCallback: DialogInterface.OnClickListener? = null,
        dismissCallback: () -> Unit = { }
    ) {
        if (isShowDialog()) {
            return
        }
        alertDialog = MaterialAlertDialogBuilder(context)
            .setTitle(titleResId)
            .setMessage(msgResId)
            .setNegativeButton(R.string.cancel, closeCallback)
            .setPositiveButton(R.string.confirm, confirmCallback)
            .setCancelable(false)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                setOnDismissListener {
                    alertDialog = null
                    dismissCallback()
                }
                show()
            }
    }

    fun showRationaleDialog(
        activity: FragmentActivity,
        requestCode: Int,
        finishActivity: Boolean,
        permissions: Array<String>
    ) {
        var isFinished: Boolean = finishActivity
        if (isShowDialog()) {
            dismiss()
        }
        alertDialog = AlertDialog.Builder(activity)
            .setMessage(R.string.permission_request)
            .setPositiveButton(android.R.string.ok) { _, _ ->
                activity.let {
                    ActivityCompat.requestPermissions(
                        it,
                        permissions,
                        requestCode
                    )
                }
                isFinished = false
            }
            .setNegativeButton(android.R.string.cancel, null)
            .setCancelable(false)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                setOnDismissListener {
                    alertDialog = null
                    if (isFinished) {
                        showDeniedDialog(activity, isFinished)
                    }
                }
                show()
            }
    }

    fun showDeniedDialog(
        activity: FragmentActivity,
        finishActivity: Boolean
    ) {
        if (isShowDialog()) {
            dismiss()
        }
        alertDialog = AlertDialog.Builder(activity)
            .setMessage(R.string.permission_denied)
            .setPositiveButton(android.R.string.ok, null)
            .setCancelable(false)
            .create()
            .apply {
                setCanceledOnTouchOutside(false)
                setOnDismissListener {
                    alertDialog = null
                    if (finishActivity) {
                        ToastHelper.show(R.string.permission_required_toast)
                        activity.finish()
                    }
                }
                show()
            }
    }

    fun dismiss() {
        alertDialog?.dismiss()
        alertDialog = null
    }
}