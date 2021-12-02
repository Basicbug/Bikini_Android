package com.example.bikini_android.ui.account

import androidx.databinding.ObservableField
import com.basicbug.core.ui.item.ItemViewModel
import com.example.bikini_android.R

/**
 * @author bsgreentea
 */
class AccountSettingItemViewModel : ItemViewModel(), AccountItemViewModel {

    override val nickname = ObservableField<String>()

    override fun getLayoutRes(): Int {
        return R.layout.fragment_account_setting
    }
}