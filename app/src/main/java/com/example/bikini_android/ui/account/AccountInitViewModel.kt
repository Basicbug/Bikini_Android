package com.example.bikini_android.ui.account

import com.example.bikini_android.BuildConfig
import com.example.bikini_android.R
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.repository.account.UserInfo
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.ktx.isNullOrBlank
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */

class AccountInitViewModel : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val disposables = CompositeDisposable()
    val accountInitItem = AccountInitItemViewModel()
    val progressViewModel = ProgressItemViewModel()

    fun setUserName() {

        if (accountInitItem.nickname.isNullOrBlank()) {
            ToastHelper.show(R.string.empty_input_box)
        } else {

            progressViewModel.isVisible = true

            AccountRepositoryImpl
                .getUserFromRemote(UserInfo(accountInitItem.nickname.get()!!))
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it == SUCCESS_MESSAGE) {
                        LoginManagerProxy.userName = accountInitItem.nickname.get()!!
                        itemEventRelay.accept(EventType.UPDATE_SUCCEED)
                        ToastHelper.show("성공")
                        progressViewModel.isVisible = false
                    }
                }, {
                    if (BuildConfig.DEBUG) {
                        ToastHelper.show(it.toString())
                    } else {
                        ToastHelper.show(R.string.unknown_error_message)
                    }
                    progressViewModel.isVisible = false
                })
                .addTo(disposables)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    enum class EventType : RxAction {
        UPDATE_SUCCEED
    }

    companion object {
        private const val SUCCESS_MESSAGE = "SUCCESS"
    }
}