/*
 * ProfileItemViewModel.kt 2021. 5. 19
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.profile

import androidx.databinding.Bindable
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.item.ItemViewModel
import com.example.bikini_android.util.bus.RxAction

/**
 * @author MyeongKi
 */
class ProfileItemViewModel : ItemViewModel() {
    @get: Bindable
    var userId = "mk5432"
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    @get: Bindable
    var writeCount = "10"
        set(value) {
            field = value
            notifyPropertyChanged(BR.writeCount)
        }

    @get: Bindable
    var likeCount = "1300"
        set(value) {
            field = value
            notifyPropertyChanged(BR.likeCount)
        }

    @get: Bindable
    var shownCount = "12 K"
        set(value) {
            field = value
            notifyPropertyChanged(BR.shownCount)
        }

    @get: Bindable
    var stateMessage = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.stateMessage)
        }

    @get: Bindable
    var feedTicketCount = "5 tickets"
        set(value) {
            field = value
            notifyPropertyChanged(BR.feedTicketCount)
        }

    fun onClickNickName() {
        itemEventRelay?.accept(EventType.OPEN_EDIT_NICKNAME)
    }

    enum class EventType : RxAction {
        OPEN_EDIT_NICKNAME,
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_profile_item
    }
}