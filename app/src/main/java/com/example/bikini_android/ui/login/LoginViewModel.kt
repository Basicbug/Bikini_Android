package com.example.bikini_android.ui.login

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author bsgreentea
 */
class LoginViewModel : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()

    val progressViewModel = ProgressItemViewModel()
    private val loginRepository = LoginRepository()
    private val disposables = CompositeDisposable()

    fun sendTokenToServer(accessToken: String) {
        progressViewModel.isVisible = true
        loginRepository
            .sendTokenToServer(accessToken)
            .subscribe({ response ->
                progressViewModel.isVisible = false
                response.result?.let {
                    LoginManagerProxy.jwt = it
                    LoginManagerProxy.successLogin()
                    itemEventRelay.accept(CompleteEvent())
                }
            }, {
                progressViewModel.isVisible = false
            })
            .addTo(disposables)
    }

    class CompleteEvent : RxAction
}