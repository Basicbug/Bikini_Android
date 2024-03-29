/*
 * ToolbarItem.kt 2020. 11. 10
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.common

import androidx.annotation.StringRes
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */

enum class ToolbarItem(val visible: Boolean, @StringRes val titleResId: Int) : RxAction {
    BIKINI_TOOLBAR(true, R.string.bikini_toolbar_title),
    FEEDS_TOOLBAR(true, R.string.feeds_toolbar_title),
    SETTINGS_TOOLBAR(true, R.string.settings_toolbar_title),
    PROFILE_TOOLBAR(true, R.string.profile_toolbar_title);
}