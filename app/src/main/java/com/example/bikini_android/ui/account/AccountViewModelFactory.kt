package com.example.bikini_android.ui.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

/**
 * @author bsgreentea
 */
class AccountViewModelFactory(
    private val accountItem: AccountItemViewModel
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(AccountSettingViewModel::class.java)) {
            AccountSettingViewModel(accountItem) as T
        } else {
            throw IllegalStateException()
        }
    }
}