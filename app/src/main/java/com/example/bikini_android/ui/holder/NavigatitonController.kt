/*
 * NavigateController.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.annotation.SuppressLint
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import com.example.bikini_android.ui.map.BikiniMapFragment
import io.reactivex.Maybe

/**
 * @author MyeongKi
 */
@SuppressLint("CheckResult")
class NavigatitonController(private val fragmentContainerId: Int, private val fragmentManager: FragmentManager) {


    private fun findFragment(fragmentClass: Class<out Fragment>): Fragment? {
        return fragmentManager.findFragmentByTag(fragmentClass.name)
    }

    private fun replaceFragment(newFragment: Fragment) {
        val fragmentTagId = newFragment.javaClass.name

        val fragmentTransaction = fragmentManager.beginTransaction()
        var fragmentToShow = fragmentManager.findFragmentByTag(fragmentTagId)

        if (fragmentToShow == null) {
            fragmentToShow = newFragment
            fragmentTransaction.add(fragmentContainerId, fragmentToShow, fragmentTagId)
        } else {
            fragmentTransaction.show(fragmentToShow)
        }
        val currentPrimaryFragment = fragmentManager.primaryNavigationFragment
        if (currentPrimaryFragment == fragmentToShow) {
            return
        }
        currentPrimaryFragment?.let {
            fragmentTransaction.hide(it)
        }
        fragmentTransaction.setPrimaryNavigationFragment(fragmentToShow)
        fragmentTransaction.setReorderingAllowed(true)
        fragmentTransaction.commitNowAllowingStateLoss()
    }

    fun navigateToBikiniMap() {
        Maybe.just(BikiniMapFragment::class.java)
            .filter { findFragment(it) != null }
            .map { findFragment(it) }
            //.doOnError { replaceFragment(BikiniMapFragment) }
            .subscribe { replaceFragment(it!!) }
    }

    fun popBackStack(): Boolean {
        return fragmentManager.popBackStackImmediate()
    }
}