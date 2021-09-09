package com.example.bikini_android.ui.profile

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

class ProfileViewModel : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val profileItemViewModel = ProfileItemViewModel().apply {
        userId = LoginManagerProxy.userName
        this.itemEventRelay = this@ProfileViewModel.itemEventRelay
    }

    fun openBoard() {
        itemEventRelay.accept(EventType.OPEN_BOARD)
    }

    enum class EventType : RxAction {
        OPEN_BOARD,
    }
}