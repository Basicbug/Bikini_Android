package com.example.bikini_android.ui.account

import androidx.databinding.ObservableField
import com.example.bikini_android.R
import com.example.bikini_android.ui.common.item.ItemViewModel

/**
 * @author bsgreentea
 */
class AccountSettingItemViewModel : ItemViewModel(), AccountItemViewModel {

    override val nickname = ObservableField<String>()

    override fun getLayoutRes(): Int {
        return R.layout.activity_account_setting
    }
}