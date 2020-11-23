/*
 * BaseActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.example.bikini_android.util.permission.OnPermissionCallback

/**
 * @author MyeongKi
 */

abstract class BaseActivity : AppCompatActivity(), OnPermissionCallback {
    private var permissionDenied = false
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super<OnPermissionCallback>.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
}