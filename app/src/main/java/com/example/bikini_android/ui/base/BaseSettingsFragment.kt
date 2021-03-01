/*
 * BaseSettingsFragment.kt 2021. 2. 19
 *
 * Copyright 2021 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.base

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.example.bikini_android.R
import com.example.bikini_android.util.logging.Logger

/**
 * @author MyeongKi
 */
abstract class BaseSettingsFragment : PreferenceFragmentCompat() {

    protected val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "SettingsFragment"
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings_main, rootKey)
    }
}