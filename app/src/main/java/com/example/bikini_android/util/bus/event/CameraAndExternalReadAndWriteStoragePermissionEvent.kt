/*
 * CameraAndExternalReadAndWriteStoragePermissionEvent.kt 2021. 8. 1
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.bus.event

import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */
class CameraAndExternalReadAndWriteStoragePermissionEvent (val isAccept: Boolean) : RxAction