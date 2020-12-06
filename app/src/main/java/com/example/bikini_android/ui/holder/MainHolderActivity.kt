/*
 * MainHolderActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModel
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.ActivityMainHolderBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.common.ToolbarItem
import com.example.bikini_android.ui.feeds.FeedsViewModel
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
    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private lateinit var viewModels: List<ViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        viewModels = MainHolderViewModelsHelper.getViewModels(this, savedInstanceState)
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)
        navigateController = NavigationController(binding.contentFragmentHolder.id, supportFragmentManager)
        setUpToolbar()
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
            bottomNavigationItem?.let {
                navigateBottomMenu(it)
            }
            true
        }
        navigateBottomMenu(BottomNavigationItem.BIKINI_MAP)
    }

    fun navigateBottomMenu(navigationItem: BottomNavigationItem) {
        navigationItem.navigate(navigateController)
        navigationItem.invoke(itemEventRelay)
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

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        MainHolderViewModelsHelper.saveInstanceState(outState, viewModels)
    }

    companion object {
        private const val KEY_FEEDS = "keyFeeds"
    }
}
