/*
 * AccountInfoItemViewModel.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.info

import androidx.databinding.Bindable
import com.basicbug.core.ui.item.ItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.BR
import com.example.bikini_android.R
import com.example.bikini_android.repository.account.UserInfo

/**
 * @author MyeongKi
 */
class AccountInfoItemViewModel : ItemViewModel() {
    @get: Bindable
    var userId = ""
        set(value) {
            field = value
            notifyPropertyChanged(BR.userId)
        }

    @get: Bindable
    var feedCount = "0"
        set(value) {
            field = value
            notifyPropertyChanged(BR.feedCount)
        }

    @get: Bindable
    var likeCount = "0"
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

    fun setUserInfo(userInfo: UserInfo?) {
        userInfo?.let {
            userId = it.userName
            likeCount = it.likeCount.toString()
            feedCount = it.feedCount.toString()
        }
    }

    fun onClickNickName() {
        itemEventRelay?.accept(EventType.OPEN_EDIT_NICKNAME)
    }

    enum class EventType : RxAction {
        OPEN_EDIT_NICKNAME,
    }

    override fun getLayoutRes(): Int {
        return R.layout.view_account_info_item
    }
}