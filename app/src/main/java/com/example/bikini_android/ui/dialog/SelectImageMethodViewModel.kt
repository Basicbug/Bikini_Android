/*
 * SelectImageNavViewModel.kt 2021. 7. 31
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.dialog

import androidx.lifecycle.ViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author MyeongKi
 */
class SelectImageMethodViewModel : ViewModel() {
    var itemEventRelay: Relay<RxAction>? = null
    val disposables = CompositeDisposable()

    fun onClickNavigateCamera() {
        itemEventRelay?.accept(EventType.NAVIGATE_CAMERA)
    }

    fun onClickNavigateGallery() {
        itemEventRelay?.accept(EventType.NAVIGATE_GALLERY)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    enum class EventType : RxAction {
        NAVIGATE_CAMERA, NAVIGATE_GALLERY;
    }
}