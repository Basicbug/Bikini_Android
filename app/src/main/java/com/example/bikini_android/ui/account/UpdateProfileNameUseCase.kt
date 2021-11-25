package com.example.bikini_android.ui.account

import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.repository.account.UserInfo
import com.basicbug.core.util.bus.RxAction
import com.basicbug.core.rx.addTo
import com.jakewharton.rxrelay2.Relay
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

/**
 * @author bsgreentea
 */
class UpdateProfileNameUseCase(
    private val disposable: CompositeDisposable,
    private val itemEventRelay: Relay<RxAction>,
) {

    fun execute(nickName: String) {

        AccountRepositoryImpl
            .getUserFromRemote(UserInfo(nickName))
            .subscribeOn(Schedulers.io())
            .subscribe({ result ->

                result?.let {
                    itemEventRelay.accept(
                        (AccountEvent(
                            nickName,
                            AccountSettingViewModel.EventType.UPDATE_SUCCEED
                        ))
                    )
                }

            }, {
                itemEventRelay.accept(
                    (AccountEvent(
                        it.toString(),
                        AccountSettingViewModel.EventType.UPDATE_FAILED
                    ))
                )
            })
            .addTo(disposable)
    }
}