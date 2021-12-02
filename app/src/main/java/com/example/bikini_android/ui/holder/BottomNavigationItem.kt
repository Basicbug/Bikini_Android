/*
 * BottomNavigationItem.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import androidx.annotation.IdRes
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.ToolbarItem
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

/**
 * @author MyeongKi
 */

enum class BottomNavigationItem(
    @IdRes val menuId: Int,
    private val eventAction: (Relay<RxAction>) -> Unit
) {
    BIKINI_MAP(
        menuId = R.id.bikini_navigation,
        eventAction = { relay -> relay.accept(ToolbarItem.BIKINI_TOOLBAR) }
    ),
    FEEDS(
        menuId = R.id.hot_feeds_navigation,
        eventAction = { relay -> relay.accept(ToolbarItem.FEEDS_TOOLBAR) }
    ),
    SETTINGS(
        menuId = R.id.settings_navigation,
        eventAction = { relay -> relay.accept(ToolbarItem.SETTINGS_TOOLBAR) }
    ),
    PROFILE(
        menuId = R.id.profile_navigation,
        eventAction = { relay -> relay.accept(ToolbarItem.PROFILE_TOOLBAR) }
    );

    fun invoke(relay: Relay<RxAction>) {
        eventAction(relay)
    }

    companion object {
        @JvmStatic
        fun findById(
            @IdRes menuId: Int,
            defaultItem: BottomNavigationItem? = BIKINI_MAP
        ): BottomNavigationItem? = Observable.fromArray(*values())
            .filter { it.menuId == menuId }
            .blockingFirst(defaultItem)
    }
}