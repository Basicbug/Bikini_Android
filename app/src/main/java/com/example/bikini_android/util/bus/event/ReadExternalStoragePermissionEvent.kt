/*
 * ReadExternalStoragePermissionEvent.kt 2021. 3. 7
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.util.bus.event

import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */
class ReadExternalStoragePermissionEvent(val isAccept: Boolean) : RxAction
