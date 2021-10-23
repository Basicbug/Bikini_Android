/*
 * AccountSettingViewModel.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.setting

import com.basicbug.core.app.ToastHelper
import com.basicbug.core.ui.base.BaseViewModel
import com.basicbug.core.ui.progress.ProgressItemViewModel
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.util.ktx.isNullOrBlank
import com.example.bikini_android.BuildConfig
import com.example.bikini_android.R
import com.example.bikini_android.manager.login.LoginManagerProxy
import com.example.bikini_android.repository.account.UserUpdateInfo
import com.example.bikini_android.ui.account.UpdateUserInfoUseCase
import com.jakewharton.rxrelay2.PublishRelay
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable

/**
 * @author bsgreentea
 */
class AccountSettingViewModel(
    val accountItem: AccountUserNameItemViewModel?
) : BaseViewModel() {

    val itemEventRelay: Relay<RxAction> = PublishRelay.create()
    private val disposables = CompositeDisposable()
    val progressViewModel = ProgressItemViewModel()

    val prevUserName = LoginManagerProxy.userName

    private val updateProfileNameUseCase = UpdateUserInfoUseCase(disposables)

    fun setUserName() {

        if (accountItem?.nickname?.isNullOrBlank() == true) {
            ToastHelper.show(R.string.empty_input_box)
        } else {

            progressViewModel.isVisible = true

            accountItem?.nickname?.get()?.let {
                updateProfileNameUseCase.execute(UserUpdateInfo(it))
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