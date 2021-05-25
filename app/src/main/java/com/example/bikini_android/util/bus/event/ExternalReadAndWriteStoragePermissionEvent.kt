/*
 * ReadExternalStoragePermissionEvent.kt 2021. 5. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.bus.event

import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */
class ExternalReadAndWriteStoragePermissionEvent(val isAccept: Boolean) : RxAction