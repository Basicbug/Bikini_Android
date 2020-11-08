/*
 * BottomNavigationItem.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import androidx.annotation.IdRes
import com.example.bikini_android.R
import io.reactivex.Observable

/**
 * @author MyeongKi
 */

enum class BottomNavigationItem(
    @IdRes val menuId: Int,
    private val navigationAction: (NavigationController) -> Unit
) {
    BIKINI_MAP(
        menuId = R.id.bikini_map_icon,
        navigationAction = { navigateController -> navigateController.navigateToBikiniMap() }
    ),
    FEEDS(
        menuId = R.id.feeds_icon,
        navigationAction = { navigationController -> navigationController.navigateToFeeds() }
    ),
    SETTINGS(
        menuId = R.id.settings_icon,
        navigationAction = { navigateController -> navigateController.navigateToSettings() }
    );

    fun navigate(navigationController: NavigationController) {
        navigationAction(navigationController)
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