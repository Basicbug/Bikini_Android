package com.example.bikini_android.ui.account

import com.basicbug.core.util.bus.RxAction
import com.example.bikini_android.ui.account.setting.AccountSettingViewModel

/**
 * @author bsgreentea
 */
class AccountEvent(val result: String, val type: AccountSettingViewModel.EventType) : RxAction