/*
 * AccountInfoViewModel.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.info

import com.basicbug.core.rx.SchedulerProvider
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.base.BaseViewModel
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.repository.account.AccountRepository
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

class AccountInfoViewModel(
    private val accountRepository: AccountRepository,
    private val schedulerProvider: SchedulerProvider
) : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val accountInfoItemViewModel = AccountInfoItemViewModel().apply {
        this.itemEventRelay = this@AccountInfoViewModel.itemEventRelay
    }
    val disposables = CompositeDisposable()

    fun updateAccountInfoUi() {
        accountRepository.getMyInfo()
            .observeOn(schedulerProvider.main())
            .subscribe { userInfo ->
                accountInfoItemViewModel.setUserInfo(userInfo)
            }
            .addTo(disposables)

    }

    fun openBoard() {
        itemEventRelay.accept(EventType.OPEN_BOARD)
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    enum class EventType : RxAction {
        OPEN_BOARD,
    }
}