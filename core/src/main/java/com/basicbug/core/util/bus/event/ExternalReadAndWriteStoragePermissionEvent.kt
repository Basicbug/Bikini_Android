/*
 * ReadExternalStoragePermissionEvent.kt 2021. 5. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.basicbug.core.util.bus.event

import com.basicbug.core.util.bus.RxAction

/**
 * @author MyeongKi
 */
class ExternalReadAndWriteStoragePermissionEvent(val isAccept: Boolean) : RxAction