/*
 * BaseActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import androidx.appcompat.app.AppCompatActivity
import com.example.bikini_android.util.permission.OnPermissionCallback
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

abstract class BaseActivity : AppCompatActivity(), OnPermissionCallback {
    private var permissionDenied = false
    protected val disposable: CompositeDisposable = CompositeDisposable()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super<OnPermissionCallback>.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }
    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
    }

}