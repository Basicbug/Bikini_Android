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
import com.basicbug.core.util.logging.Logger
import com.example.bikini_android.R

/**
 * @author MyeongKi
 */

abstract class NavigationHelper {
    private var activity: MainHolderActivity? = null

    private val logger = Logger().apply {
        TAG = this.javaClass.simpleName
    }

    fun setActivity(activity: MainHolderActivity) {
        this.activity = activity
    }

    fun navigateToFeeds(bundle: Bundle) {
        getNavController()?.navigate(R.id.action_feeds_end, bundle)
    }

    fun navigateToAccountSetting() {
        getNavController()?.navigate(R.id.account_setting)
    }

    fun navigateToProfileDetail() {
        getNavController()?.navigate(R.id.action_detail)
    }

    fun navigateToFeedsMap(bundle: Bundle) {
        getNavController()?.navigate(R.id.action_feed_map, bundle)
    }

    fun navigateToSettings(bundle: Bundle) {
        getNavController()?.navigate(R.id.action_settings, bundle)
    }

    fun clear() {
        activity = null
    }

    fun popBackStack() {
        getNavController()?.popBackStack()
    }

    protected fun getNavController(): NavController? {
        return activity?.findNavController(R.id.content_fragment_holder)
    }
}
