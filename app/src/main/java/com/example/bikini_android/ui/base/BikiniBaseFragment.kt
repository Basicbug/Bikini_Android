/*
 * BikiniBaseFragment.kt 2021. 12. 2
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import com.basicbug.core.ui.base.BaseFragment
import com.example.bikini_android.ui.holder.MainHolderActivity
import com.example.bikini_android.ui.holder.NavigationHelperImpl

/**
 * @author MyeongKi
 */
abstract class BikiniBaseFragment : BaseFragment() {
    protected fun getNavigationHelper(): NavigationHelperImpl? {
        return if (activity is MainHolderActivity) {
            (activity as MainHolderActivity).navigationHelper
        } else {
            null
        }
    }
}