package com.example.bikini_android.ui.login

import com.basicbug.core.rx.DefaultSchedulerProvider
import com.basicbug.core.rx.SchedulerProvider
import com.basicbug.core.rx.addTo
import com.basicbug.core.ui.base.BaseViewModel
import com.basicbug.core.ui.progress.ProgressItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.error.ErrorToastHelper
import com.basicbug.core.util.logging.Logger
import com.basicbug.network.response.TokenResponse
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.account.AccountRepositoryImpl
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

    @Provides
    @ViewModelScoped
    fun provideScheduler(): SchedulerProvider = DefaultSchedulerProvider()

    @Provides
    @ViewModelScoped
    fun provideLoginManager(): LoginManagerProxy = LoginManagerProxy

    @Provides
    @ViewModelScoped
    fun provideAccountRepo(): AccountRepositoryImpl = AccountRepositoryImpl
}

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginRepository: LoginRepository,
    private val schedulerProvider: SchedulerProvider,
    private val loginManager: LoginManagerProxy,
    private val accountRepository: AccountRepositoryImpl
) : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    val progressViewModel = ProgressItemViewModel()
    private val disposables = CompositeDisposable()
    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "LoginViewModel"
        }
    }

    fun sendTokenToServer(accessToken: String) {
        progressViewModel.isVisible = true
        loginRepository
            .sendTokenToServer(accessToken)
            .subscribeOn(schedulerProvider.io())
            .onErrorReturn {
                ErrorToastHelper.unknownError(logger, it)
                TokenResponse.Result("", "")
            }
            .subscribe({ result ->
                progressViewModel.isVisible = false
                result?.let {
                    loginManager.accessToken = it.accessToken
                    loginManager.refreshToken = it.refreshToken
                    loginManager.successLogin()
                    itemEventRelay.accept(EventType.COMPLETE)
                }
            }, {
                progressViewModel.isVisible = false
            })
            .addTo(disposables)
    }

    fun checkMyInfo() {
        accountRepository
            .getMyInfo()
            .subscribeOn(schedulerProvider.io())
            .subscribe({

                if (it != null) {
                    loginManager.userName = it.userName
                    itemEventRelay.accept(EventType.ALREADY_EXIST)
                } else {
                    itemEventRelay.accept(EventType.NO_INFO)
                }

            }, {
                ErrorToastHelper.unknownError(logger, it)
            })
            .addTo(disposables)
    }

    enum class EventType : RxAction {
        COMPLETE, ALREADY_EXIST, NO_INFO
    }

}