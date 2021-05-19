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
    var stateMessage = "허허 인생~ 새로운 기능을 추가해야지.."
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

    override fun getLayoutRes(): Int {
        return R.layout.view_profile_item
    }
}