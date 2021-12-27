package com.example.bikini_android.ui.account

import com.basicbug.core.rx.addTo
import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.repository.account.UserUpdateInfo
import com.example.bikini_android.ui.account.setting.AccountSettingEvent
import com.example.bikini_android.ui.account.setting.AccountSettingViewModel
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */
class UpdateUserInfoUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>
) {

    fun execute(userUpdateInfo: UserUpdateInfo) {
        AccountRepositoryImpl
            .updateUserInfo(userUpdateInfo)
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->
                result?.let {
                    itemEventRelay.accept(
                        AccountSettingEvent(
                            it.userName,
                            AccountSettingViewModel.EventType.UPDATE_SUCCEED
                        )
                    )
                }

            }, {
                itemEventRelay.accept(
                    AccountSettingEvent(
                        it.toString(),
                        AccountSettingViewModel.EventType.UPDATE_FAILED
                    )
                )
            })
            .addTo(disposable)
    }
}