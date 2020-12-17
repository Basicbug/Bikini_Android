package com.example.bikini_android.ui.board

import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.util.bus.RxAction
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay

class BoardViewModel : BaseViewModel() {
    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val boardItemViewModel = BoardItemViewModel(itemEventRelay)
}