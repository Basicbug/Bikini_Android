package com.example.bikini_android.ui.account

import com.example.bikini_android.util.bus.RxAction

/**
 * @author bsgreentea
 */
class AccountEvent(val result: String, val type: AccountSettingViewModel.EventType) : RxAction