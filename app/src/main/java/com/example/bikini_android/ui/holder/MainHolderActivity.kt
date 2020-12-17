/*
 * MainHolderActivity.kt 2020. 10. 31
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.holder

import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.databinding.ActivityMainHolderBinding
import com.example.bikini_android.ui.base.BaseActivity
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.common.ToolbarItem
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers

/**
 * @author MyeongKi
 */

class MainHolderActivity : BaseActivity() {

    lateinit var binding: ActivityMainHolderBinding
    private val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private lateinit var viewModels: List<BaseViewModel>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main_holder)
        viewModels = MainHolderViewModelsHelper.getViewModels(this)
        setUpToolbar()
        setUpBottomNavigation()
    }

    private fun setUpBottomNavigation() {
        val navGraphIds =
            listOf(R.navigation.bikini, R.navigation.hot_feeds, R.navigation.settings, R.navigation.profile)
        binding.bottomNavigation.setupNavController(
            navGraphIds,
            supportFragmentManager,
            R.id.content_fragment_holder,
            itemEventRelay
        )
    }

    private fun setUpToolbar() {
        this.setSupportActionBar(binding.toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(false)
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
        MainHolderViewModelsHelper.saveState(viewModels)
    }
}
