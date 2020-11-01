/*
 * MainHolderActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.example.bikini_android.R
import com.example.bikini_android.databinding.ActivityMainHolderBinding
import com.example.bikini_android.ui.base.BaseActivity

/**
 * @author MyeongKi
 */

class MainHolderActivity : BaseActivity() {

    lateinit var binding: ActivityMainHolderBinding
    lateinit var navigateController: NavigationController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)
        navigateController = NavigationController(binding.contentFragmentHolder.id, supportFragmentManager)
        setUpBottomNavigation()
    }

    override fun onBackPressed() {
        if (navigateController.popBackStack())
            return
        super.onBackPressed()
    }

    private fun setUpBottomNavigation() {
        binding.bottomNavigation.setOnNavigationItemSelectedListener { menuItem ->
            val bottomNavigationItem = BottomNavigationItem.findById(menuItem.itemId)
            bottomNavigationItem?.navigate(navigateController)
            true
        }
        navigateBottomMenu(BottomNavigationItem.BIKINI_MAP)
    }

    fun navigateBottomMenu(navigationItem: BottomNavigationItem) {
        if (binding.bottomNavigation.selectedItemId != navigationItem.menuId) {
            binding.bottomNavigation.selectedItemId = navigationItem.menuId
        }
        navigationItem.navigate(navigateController)
    }
}