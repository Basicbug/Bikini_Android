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
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.ActivityMainHolderBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.common.ToolbarItem
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */

class MainHolderActivity : BaseActivity() {

    lateinit var binding: ActivityMainHolderBinding
    lateinit var navigateController: NavigationController

    private val disposable: CompositeDisposable = CompositeDisposable()
    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)
        navigateController = NavigationController(binding.contentFragmentHolder.id, supportFragmentManager)
        setUpBottomNavigation()
        setUpToolbar()
    }

    override fun onDestroy() {
        super.onDestroy()
        disposable.clear()
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
            bottomNavigationItem?.invoke(itemEventRelay)
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

    private fun setUpToolbar() {
        this.setSupportActionBar(binding.toolbar)
        itemEventRelay
            .observeOn(AndroidSchedulers.mainThread())
            .ofType(ToolbarItem::class.java)
            .subscribe { event ->
                this.supportActionBar?.let {
                    it.title = AppResources.getStringResId(event.titleResId)
                    if (event.visible) {
                        it.show()
                    } else {
                        it.hide()
                    }
                }

            }.addTo(disposable)
    }
}
