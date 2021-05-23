/*
 * BaseActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bikini_android.util.bus.RxActionBus
import com.example.bikini_android.util.permission.PermissionEventFactory
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

abstract class BaseActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    protected val disposables: CompositeDisposable = CompositeDisposable()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        PermissionEventFactory.create(requestCode, permissions, grantResults)?.let {
            RxActionBus.post(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}