package com.example.bikini_android.ui.account

import androidx.databinding.ObservableField
import com.example.bikini_android.R
import com.basicbug.core.ui.item.ItemViewModel

/**
 * @author bsgreentea
 */
class AccountInitItemViewModel : ItemViewModel(), AccountItemViewModel {

    override val nickname = ObservableField<String>()

    override fun getLayoutRes(): Int {
        return R.layout.activity_account_init
    }
}