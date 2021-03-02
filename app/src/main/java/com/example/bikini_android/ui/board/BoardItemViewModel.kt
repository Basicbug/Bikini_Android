/*
 * BoardItemViewModel.kt 2020. 12. 17
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.board

import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */

class BoardItemViewModel(itemEventRelay: Relay<RxAction>) : ItemViewModel() {
    val content = ObservableField<String>()

    @get:Bindable
    var imageUrl =""
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }

    init {
        this.itemEventRelay = itemEventRelay
    }

    fun navigateToGallery() {
        itemEventRelay?.accept(EventType.NAVIGATE_GALLERY)
    }

    fun postFeed() {
        itemEventRelay?.accept(EventType.POST_FEED)
    }

    fun getImageLoadEvent() = Unit

    override fun getLayoutRes(): Int {
        return R.layout.activity_board
    }

    enum class EventType : RxAction {
        NAVIGATE_GALLERY, POST_FEED, FINISH;
    }
}
