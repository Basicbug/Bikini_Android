/*
 * BaseActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.example.bikini_android.util.permission.OnRequestLocationPermissionUseCase
import com.example.bikini_android.util.permission.OnRequestReadExternalStoragePermissionUseCase
import com.example.bikini_android.util.permission.PermissionUtils
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

abstract class BaseActivity : AppCompatActivity(),
    ActivityCompat.OnRequestPermissionsResultCallback {

    protected val disposables: CompositeDisposable = CompositeDisposable()
    private val locationPermissionUseCase = OnRequestLocationPermissionUseCase()
    private val readExternalStoragePermissionUseCase =
        OnRequestReadExternalStoragePermissionUseCase()

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        if (requestCode == PermissionUtils.LOCATION_PERMISSION_REQUEST_CODE) {
            locationPermissionUseCase.execute(permissions, grantResults)
        } else if (requestCode == PermissionUtils.READ_EXTERNAL_STORAGE_PERMISSION_REQUEST_CODE) {
            readExternalStoragePermissionUseCase.execute(permissions, grantResults)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        disposables.clear()
    }
}