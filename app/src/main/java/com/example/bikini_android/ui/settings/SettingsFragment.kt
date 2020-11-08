/*
 * SettingsFragment.kt 2020. 11. 1
 *
 * Copyright 2020 BasicBug. All rights Reserved.
 *
 */

package com.example.bikini_android.ui.settings

import android.os.Bundle
import android.view.View
import androidx.preference.EditTextPreference
import androidx.preference.Preference
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.example.bikini_android.R
import com.example.bikini_android.util.ktx.pref
import com.example.bikini_android.util.logging.Logger

/**
 * @author MyeongKi
 */

class SettingsFragment : PreferenceFragmentCompat() {

    private val logger: Logger by lazy {
        Logger().apply {
            TAG = "AddingFeedFragment"
        }
    }

    private val PREF_DEV_MODE = "pref.dev_mode"
    private val PREF_BASE_URL = "pref.base_url"
    private val DEV_BASE_URL = "dev.base.url"
    private val PREF_DEV_CATEGORY = "pref.dev_category"

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_settings_main, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<SwitchPreferenceCompat>(PREF_DEV_MODE)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                logger.debug { "dev mode $newValue" }
                findPreference<PreferenceCategory>(PREF_DEV_CATEGORY)?.isEnabled = newValue as Boolean
                true
            }

        findPreference<EditTextPreference>(PREF_BASE_URL)?.onPreferenceChangeListener =
            Preference.OnPreferenceChangeListener { _, newValue ->
                logger.debug { "changed base url $newValue" }
                with(requireActivity().pref()) {
                    edit().putString(DEV_BASE_URL, newValue as String).commit()
                }
                true
            }
    }

    companion object {
        fun newInstance(): SettingsFragment {
            return SettingsFragment()
        }
    }
}