package com.example.bikini_android.ui.account

import com.example.bikini_android.BuildConfig
import com.example.bikini_android.R
import com.example.bikini_android.app.ToastHelper
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.ui.base.BaseViewModel
import com.example.bikini_android.ui.progress.ProgressItemViewModel
import com.example.bikini_android.util.bus.RxAction
import com.example.bikini_android.util.ktx.isNullOrBlank
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author bsgreentea
 */
class AccountSettingViewModel(
    val accountItem: AccountItemViewModel
) : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val disposables = CompositeDisposable()
    val progressViewModel = ProgressItemViewModel()

    val prevUserName = LoginManagerProxy.userName

    private val updateProfileNameUseCase = UpdateProfileNameUseCase(disposables, itemEventRelay)

    fun setUserName() {

        if (accountItem.nickname.isNullOrBlank()) {
            ToastHelper.show(R.string.empty_input_box)
        } else {

            progressViewModel.isVisible = true

            accountItem.nickname.get()?.let {
                updateProfileNameUseCase.execute(it)
            }
        }
    }

    fun showError(msg: String) {
        if (BuildConfig.DEBUG) {
            ToastHelper.show(msg)
        } else {
            ToastHelper.show(R.string.unknown_error_message)
        }
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

    enum class EventType : RxAction {
        UPDATE_SUCCEED, UPDATE_FAILED
    }

}