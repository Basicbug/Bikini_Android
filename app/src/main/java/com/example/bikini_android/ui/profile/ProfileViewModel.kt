package com.example.bikini_android.ui.profile

import android.util.Log
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

class ProfileViewModel : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    fun openBoard() {
        Log.d("asdfasdf", "Asdfasdfasd")
        itemEventRelay.accept(EventType.OPEN_BOARD)
    }

    enum class EventType : RxAction {
        OPEN_BOARD
    }

}