/*
 * ToolbarItem.kt 2020. 11. 8
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */

data class ToolbarItem(val visible: Boolean, val title: String):RxAction