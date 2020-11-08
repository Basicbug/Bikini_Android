/*
 * BottomNavigationItem.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import androidx.annotation.IdRes
import com.example.bikini_android.R
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay
import io.reactivex.Observable

/**
 * @author MyeongKi
 */

enum class BottomNavigationItem(
    @IdRes val menuId: Int,
    private val navigationAction: (NavigationController) -> Unit,
    private val eventAction: (Relay<RxAction>) -> Unit
) {
    BIKINI_MAP(
        menuId = R.id.bikini_map_icon,
        navigationAction = { navigateController -> navigateController.navigateToBikiniMap() },
        eventAction = { relay ->  relay.accept(ToolbarItem(true, "test1"))}
    ),
    ADDING_CONTENTS(
        menuId = R.id.adding_feed_icon,
        navigationAction = { navigateController -> navigateController.navigateToAddingFeed() },
        eventAction = { relay ->  relay.accept(ToolbarItem(false, "test2"))}
    ),
    SETTINGS(
        menuId = R.id.settings_icon,
        navigationAction = { navigateController -> navigateController.navigateToSettings() },
        eventAction = { relay ->  relay.accept(ToolbarItem(true, "test3"))}
    );

    fun navigate(navigationController: NavigationController) {
        navigationAction(navigationController)
    }
    fun invoke(relay:Relay<RxAction>) {
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