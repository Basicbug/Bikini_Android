/*
 * BaseActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.basicbug.core.util.bus.RxActionBus
import com.basicbug.core.util.permission.PermissionEventFactory
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
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        PermissionEventFactory.create(requestCode, permissions, grantResults)?.let {
            RxActionBus.post(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}