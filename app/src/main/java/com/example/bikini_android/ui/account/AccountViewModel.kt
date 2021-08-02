package com.example.bikini_android.ui.account

import android.util.Log
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.repository.account.UserInfo
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */

class AccountViewModel : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val disposables = CompositeDisposable()
    val accountInitItem = AccountInitItemViewModel()
    val progressViewModel = ProgressItemViewModel()

    fun setUserName() {

        if (accountInitItem.nickname.get().isNullOrBlank()) {
            ToastHelper.show("입력하라고")
        } else {

            progressViewModel.isVisible = true

            AccountRepositoryImpl
                .getUserFromRemote(UserInfo(accountInitItem.nickname.get()!!))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it == "1000") {
                        itemEventRelay.accept(EventType.UPDATE_SUCCEED)
                        ToastHelper.show("성공")
                        progressViewModel.isVisible = false
                    }
                }, {
                    Log.e("asdf", it.toString())
                    ToastHelper.show(it.toString())
                    progressViewModel.isVisible = false
                })
                .addTo(disposables)
        }
    }

    enum class EventType : RxAction {
        UPDATE_SUCCEED
    }
}