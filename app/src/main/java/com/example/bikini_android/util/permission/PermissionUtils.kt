/*
 * PermissionUtils.kt 2020. 11. 23
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.permission

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.pm.PackageManager
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.fragment.app.DialogFragment
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */

object PermissionUtils {
    const val LOCATION_PERMISSION_REQUEST_CODE = 1
    const val READ_AND_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE = 2

    @JvmStatic
    fun requestPermission(
        activity: AppCompatActivity,
        requestId: Int,
        permissions: Array<String>,
        finishActivity: Boolean
    ) {
        for (permission in permissions) {
            if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, permission)) {
                ActivityCompat.requestPermissions(
                    activity,
                    permissions,
                    requestId
                )
                return
            }
        }
        RationaleDialog.newInstance(requestId, finishActivity, permissions)
            .show(activity.supportFragmentManager, "dialog")
    }

    @JvmStatic
    fun isPermissionGranted(
        grantPermissions: Array<String>, grantResults: IntArray
    ): Boolean {
        for (i in grantPermissions.indices) {
            if (grantResults[i] != PackageManager.PERMISSION_GRANTED) {
                return false
            }
        }
        return true
    }

    class PermissionDeniedDialog : DialogFragment() {
        private var finishActivity = false
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            finishActivity =
                arguments?.getBoolean(ARGUMENT_FINISH_ACTIVITY) ?: false
            return AlertDialog.Builder(activity)
                .setMessage(R.string.location_permission_denied)
                .setPositiveButton(android.R.string.ok, null)
                .create()
        }

        override fun onDismiss(dialog: DialogInterface) {
            super.onDismiss(dialog)
            if (finishActivity) {
                Toast.makeText(
                    activity, R.string.permission_required_toast,
                    Toast.LENGTH_SHORT
                ).show()
                activity?.finish()
            }
        }

        companion object {
            private const val ARGUMENT_FINISH_ACTIVITY = "finish"

            @JvmStatic
            fun newInstance(finishActivity: Boolean): PermissionDeniedDialog {
                val arguments = Bundle().apply {
                    putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                }
                return PermissionDeniedDialog().apply {
                    this.arguments = arguments
                }
            }
        }
    }

    class RationaleDialog : DialogFragment() {
        private var finishActivity = false
        override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
            val requestCode =
                arguments?.getInt(ARGUMENT_PERMISSION_REQUEST_CODE) ?: 0
            finishActivity =
                arguments?.getBoolean(ARGUMENT_FINISH_ACTIVITY) ?: false
            val permissions =
                arguments?.getStringArray(ARGUMENT_PERMISSIONS) ?: arrayOf<String>()
            return AlertDialog.Builder(activity)
                .setMessage(R.string.permission_rationale_default)
                .setPositiveButton(android.R.string.ok) { _, _ ->
                    activity?.let {
                        ActivityCompat.requestPermissions(
                            it,
                            permissions,
                            requestCode
                        )
                    }
                    finishActivity = false
                }
                .setNegativeButton(android.R.string.cancel, null)
                .create()
        }

        override fun onDismiss(dialog: DialogInterface) {
            super.onDismiss(dialog)
            if (finishActivity)
                PermissionDeniedDialog.newInstance(true)
                    .show(requireActivity().supportFragmentManager, "dialog")
        }

        companion object {
            private const val ARGUMENT_PERMISSION_REQUEST_CODE = "requestCode"
            private const val ARGUMENT_FINISH_ACTIVITY = "finish"
            private const val ARGUMENT_PERMISSIONS = "permissions"

            fun newInstance(
                requestCode: Int,
                finishActivity: Boolean,
                permissions: Array<String>
            ): RationaleDialog {
                val arguments = Bundle().apply {
                    putInt(ARGUMENT_PERMISSION_REQUEST_CODE, requestCode)
                    putBoolean(ARGUMENT_FINISH_ACTIVITY, finishActivity)
                    putStringArray(ARGUMENT_PERMISSIONS, permissions)
                }
                return RationaleDialog().apply {
                    this.arguments = arguments
                }
            }
        }
    }
}