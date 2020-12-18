/*
 * NavigationHelper.kt 2020. 12. 18
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.ui.common.ToolbarItem
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import java.util.*

/**
 * @author MyeongKi
 */

class NavigationHelper(
    private val bottomNav: BottomNavigationView,
    private val activity: MainHolderActivity,
    itemRelay: Relay<RxAction>
) {
    private val disposables = CompositeDisposable()
    private val taskQueue: Queue<() -> Unit> = LinkedList<() -> Unit>()

    init {
        itemRelay
            .observeOn(AndroidSchedulers.mainThread())
            .ofType(CompleteChangeNavEvent::class.java)
            .subscribe {
                taskQueue.poll()?.invoke()
            }.addTo(disposables)
    }

    fun navigateToBikiniFeeds(bundle: Bundle) {
        if (bottomNav.selectedItemId != R.id.bikini_navigation) {
            taskQueue.offer { getNavController().navigate(R.id.action_bikini_feeds, bundle) }
            bottomNav.selectedItemId = R.id.bikini_navigation

        } else {
            getNavController().navigate(R.id.action_bikini_feeds, bundle)
        }
    }

    fun navigateToBikiniMap(bundle: Bundle) {
        if (bottomNav.selectedItemId != R.id.bikini_navigation) {
            taskQueue.offer { getNavController().navigate(R.id.action_bikini_map, bundle) }
            bottomNav.selectedItemId = R.id.bikini_navigation

        } else {
            getNavController().navigate(R.id.action_bikini_map, bundle)
        }
    }

    fun clear() {
        disposables.dispose()
    }

    private fun getNavController(): NavController {
        return activity.findNavController(R.id.content_fragment_holder)
    }
}