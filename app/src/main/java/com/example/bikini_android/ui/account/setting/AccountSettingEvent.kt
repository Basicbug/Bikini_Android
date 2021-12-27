/*
 * AccountSettingEvent.kt 2021. 10. 23
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.account.setting

import com.basicbug.core.util.bus.RxAction
/**
 * @author bsgreentea
 */
class AccountSettingEvent(val result: String, val type: AccountSettingViewModel.EventType) : RxAction