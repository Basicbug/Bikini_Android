/*
 * BoardItemViewModel.kt 2020. 12. 17
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.board

import android.net.Uri
import androidx.databinding.Bindable
import androidx.databinding.ObservableField
import com.basicbug.core.ui.item.ItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.jakewharton.rxrelay2.Relay

/**
 * @author MyeongKi
 */

class BoardItemViewModel(itemEventRelay: Relay<RxAction>, content: String?, imageUri: Uri?) : ItemViewModel() {
    val content = ObservableField<String>(content)

    @get:Bindable
    var imageUrl = imageUri.toString()
        set(value) {
            field = value
            notifyPropertyChanged(BR.imageUrl)
        }

    init {
        this.itemEventRelay = itemEventRelay
    }

    fun navigateToSelectImageMethod() {
        itemEventRelay?.accept(EventType.NAVIGATE_SELECT_IMAGE_METHOD)
    }

    fun postFeed() {
        itemEventRelay?.accept(EventType.POST_FEED)
    }

    fun getImageLoadEvent() = Unit

    override fun getLayoutRes(): Int {
        return R.layout.activity_board
    }

    enum class EventType : RxAction {
        NAVIGATE_SELECT_IMAGE_METHOD, POST_FEED, FINISH;
    }
}
