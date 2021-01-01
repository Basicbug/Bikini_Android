/*
 * NavigationHelper.kt 2020. 12. 18
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.annotation.IdRes
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.example.bikini_android.R
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.logging.Logger
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
    private val logger = Logger().apply {
        TAG = this.javaClass.simpleName
    }

    init {
        itemRelay
            .observeOn(AndroidSchedulers.mainThread())
            .ofType(CompleteChangeNavEvent::class.java)
            .doOnError {
                logger.error { it.toString() }
            }
            .subscribe {
                taskQueue.poll()?.invoke()
            }.addTo(disposables)
    }

    fun navigateToBikiniFeeds(bundle: Bundle) {
        if (isValidBottomNav(R.id.bikini_navigation)) {
            navigateToFeeds(bundle).invoke()
        } else {
            taskQueue.offer(navigateToFeeds(bundle))
            bottomNav.selectedItemId = R.id.bikini_navigation
        }
    }

    fun navigateToBikiniMap(bundle: Bundle) {
        if (isValidBottomNav(R.id.bikini_navigation)) {
            navigateToMap(bundle).invoke()
        } else {
            taskQueue.offer(navigateToMap(bundle))
            bottomNav.selectedItemId = R.id.bikini_navigation
        }
    }

    private fun navigateToFeeds(bundle: Bundle): () -> Unit = {
        getNavController().navigate(R.id.action_bikini_feeds, bundle)
    }

    private fun navigateToMap(bundle: Bundle): () -> Unit = {
        getNavController().navigate(R.id.action_bikini_map, bundle)
    }

    @Suppress("SameParameterValue")
    private fun isValidBottomNav(@IdRes resId: Int): Boolean {
        return bottomNav.selectedItemId == resId
    }

    fun clear() {
        disposables.dispose()
    }

    private fun getNavController(): NavController {
        return activity.findNavController(R.id.content_fragment_holder)
    }
}