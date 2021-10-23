package com.example.bikini_android.ui.account

import com.basicbug.core.rx.addTo
import com.basicbug.core.util.bus.RxActionBus
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.repository.account.UserUpdateInfo
import com.example.bikini_android.ui.account.setting.AccountSettingEvent
import com.example.bikini_android.ui.account.setting.AccountSettingViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */
class UpdateUserInfoUseCase(
    private val disposable: CompositeDisposable
) {

    fun execute(userUpdateInfo: UserUpdateInfo) {
        AccountRepositoryImpl
            .updateUserInfo(userUpdateInfo)
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                result?.let {
                    RxActionBus.post(
                        AccountSettingEvent(
                            it.userName,
                            AccountSettingViewModel.EventType.UPDATE_SUCCEED
                        )
                    )
                }

            }, {
                RxActionBus.post(
                    AccountSettingEvent(
                        it.toString(),
                        AccountSettingViewModel.EventType.UPDATE_FAILED
                    )
                )
            })
            .addTo(disposable)
    }
}