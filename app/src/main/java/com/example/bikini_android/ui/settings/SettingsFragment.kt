/*
 * SettingsFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import android.os.Bundle
import android.view.View
import androidx.preference.ListPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.bikini_android.R
import com.example.bikini_android.util.ktx.pref
import com.example.bikini_android.util.ktx.put
import com.example.bikini_android.util.logging.Logger
import kotlin.LazyThreadSafetyMode.NONE

/**
 * @author MyeongKi
 */

class SettingsFragment : PreferenceFragmentCompat() {

    private val logger: Logger by lazy(NONE) {
        Logger().apply {
            TAG = "SettingsFragment"
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings_main, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<SwitchPreferenceCompat>(PREF_DEV_MODE)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                logger.debug { "dev mode $newValue" }
                findPreference<PreferenceCategory>(PREF_DEV_CATEGORY)?.isEnabled =
                    newValue as Boolean
                true
            }

        findPreference<ListPreference>(PREF_SERVER_TYPE)?.apply {
            onPreferenceChangeListener = Preference.OnPreferenceChangeListener { preference, serverType ->
                logger.debug { "changed serverType $serverType" }
                with(requireActivity().pref()) {
                    put(PREF_SERVER_TYPE, serverType)
                }
                preference.summary = serverType.toString()
                true
            }
            summary = value
        }
    }

    companion object {
        const val PREF_SERVER_TYPE = "pref.server.type"
        const val PREF_DEV_MODE = "pref.dev_mode"
        private const val PREF_DEV_CATEGORY = "pref.dev_category"
    }
}
