/*
 * SettingsImageCompressionViewModel.kt 2021. 6. 13
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings.image

import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.item.ItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.manager.PreferenceManagerImpl
import com.example.bikini_android.ui.settings.SettingsViewModel
import com.example.bikini_android.ui.settings.item.SettingsCheckItemViewModel
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */
class SettingsImageCompressionViewModel : SettingsViewModel() {
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val disposables = CompositeDisposable()
    private val headItemViewModel =
        SettingsImageCompressionConfigItemProvider.createImageCompressionHeadItem()
    private val compressionItemViewModels: List<SettingsCheckItemViewModel> =
        SettingsImageCompressionConfigItemProvider.createCompressionItems(itemEventRelay)

    init {
        checkCompressionItem(PreferenceManagerImpl.getImageCompressionRate())
        itemEventRelay
            .ofType(SettingsImageCompressionRateEvent::class.java)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe { event ->
                PreferenceManagerImpl.setImageCompressionRate(event.rate)
                checkCompressionItem(event.rate)
            }.addTo(disposables)
    }

    override fun getSettingsItemViewModels(): List<ItemViewModel> {
        return mutableListOf<ItemViewModel>().apply {
            add(headItemViewModel)
            addAll(compressionItemViewModels)
        }
    }

    private fun checkCompressionItem(rateChecked: SettingsImageCompressionRate) {
        for (compressionType in SettingsImageCompressionRate.values()) {
            compressionItemViewModels[compressionType.index].isChecked =
                rateChecked == compressionType
        }
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    class SettingsImageCompressionRateEvent(val rate: SettingsImageCompressionRate) : RxAction
}