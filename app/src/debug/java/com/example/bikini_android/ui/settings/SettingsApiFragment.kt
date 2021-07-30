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
import androidx.preference.PreferenceFragmentCompat
import com.example.bikini_android.R
import com.example.bikini_android.app.AppResources
import com.example.bikini_android.util.ktx.pref
import com.example.bikini_android.util.ktx.put
import com.example.bikini_android.util.logging.Logger

/**
 * @author MyeongKi
 */

class SettingsApiFragment : PreferenceFragmentCompat() {
    private val logger: Logger by lazy(LazyThreadSafetyMode.NONE) {
        Logger().apply {
            TAG = "DeveloperSettingsFragment"
        }
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference_api_settings, rootKey)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        findPreference<ListPreference>(AppResources.getString(R.string.perf_server_type))?.apply {
            onPreferenceChangeListener =
                Preference.OnPreferenceChangeListener { preference, serverType ->
                    logger.debug { "changed serverType $serverType" }
                    with(requireActivity().pref()) {
                        put(
                            AppResources.getString(R.string.perf_server_type),
                            serverType.toString()
                        )
                    }
                    preference.summary = serverType.toString()
                    true
                }
            summary = value
        }
    }
}
