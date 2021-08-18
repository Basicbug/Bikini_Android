package com.example.bikini_android.ui.login

import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.rx.DefaultSchedulerProvider
import com.example.bikini_android.util.rx.SchedulerProvider
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
import io.reactivex.schedulers.Schedulers
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

    @Provides
    @ViewModelScoped
    fun provideScheduler(): SchedulerProvider = DefaultSchedulerProvider()

    @Provides
    @ViewModelScoped
    fun provideLoginManager(): LoginManagerProxy = LoginManagerProxy
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val schedulerProvider: SchedulerProvider,
    private val loginManager: LoginManagerProxy
) : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val progressViewModel = ProgressItemViewModel()
    private val disposables = CompositeDisposable()

    fun sendTokenToServer(accessToken: String) {
        progressViewModel.isVisible = true
        loginRepository
            .sendTokenToServer(accessToken)
            .subscribeOn(schedulerProvider.io())
            .subscribe({ result ->
                progressViewModel.isVisible = false
                result?.token?.let {
                    loginManager.jwt = it
                    loginManager.successLogin()
                    itemEventRelay.accept(EventType.COMPLETE)
                }
            }, {
                progressViewModel.isVisible = false
            })
            .addTo(disposables)
    }

    fun checkMyInfo() {
        AccountRepositoryImpl
            .getMyInfoFromRemote()
            .subscribeOn(Schedulers.io())
            .subscribe({
                if (it?.second == SUCCESS_CODE && it.first != null) {
                    LoginManagerProxy.userName = it.first!!.userName
                    itemEventRelay.accept(EventType.ALREADY_EXIST)
                } else {
                    itemEventRelay.accept(EventType.NO_INFO)
                }

            }, {

            })
            .addTo(disposables)
    }

    enum class EventType : RxAction {
        COMPLETE, ALREADY_EXIST, NO_INFO
    }

    companion object {
        private const val SUCCESS_CODE = "1000"
    }
}