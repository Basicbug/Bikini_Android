package com.example.bikini_android.ui.login

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.addTo
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * @author bsgreentea
 */

@Module
@InstallIn(ViewModelComponent::class)
internal object ViewModelLoginModule {
    @Provides
    @ViewModelScoped
    fun provideRepo() = LoginRepository()
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository
) : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val progressViewModel = ProgressItemViewModel()
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