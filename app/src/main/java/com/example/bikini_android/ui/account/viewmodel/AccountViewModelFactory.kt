package com.example.bikini_android.ui.account.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.basicbug.core.rx.DefaultSchedulerProvider
import com.example.bikini_android.repository.account.AccountRepositoryImpl
import com.example.bikini_android.ui.account.info.AccountInfoViewModel
import com.example.bikini_android.ui.account.setting.AccountSettingViewModel
import com.example.bikini_android.ui.account.setting.AccountUserNameItemViewModel

/**
 * @author bsgreentea
 */
class AccountViewModelFactory(
    private val accountItem: AccountUserNameItemViewModel? = null
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(AccountSettingViewModel::class.java) -> {
                AccountSettingViewModel(accountItem) as T
            }
            modelClass.isAssignableFrom(AccountInfoViewModel::class.java) -> AccountInfoViewModel(
                AccountRepositoryImpl,
                DefaultSchedulerProvider()
            ) as T
            else -> {
                throw IllegalStateException()
            }
        }
    }
}